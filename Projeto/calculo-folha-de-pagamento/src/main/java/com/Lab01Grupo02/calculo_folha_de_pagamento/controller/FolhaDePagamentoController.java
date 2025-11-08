@RestController
@RequestMapping("/api/folhapagamento")
public class FolhaDePagamentoController {

    private final FuncionarioService funcionarioService;
    private final ICalculadora calculadoraService;
    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final ItemFolhaRepository itemFolhaRepository;

    @Autowired
    public FolhaDePagamentoController(
            FuncionarioService funcionarioService,
            ICalculadora calculadoraService,
            FolhaPagamentoRepository folhaPagamentoRepository,
            ItemFolhaRepository itemFolhaRepository
    ) {
        this.funcionarioService = funcionarioService;
        this.calculadoraService = calculadoraService;
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.itemFolhaRepository = itemFolhaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<FolhaDePagamento> gerarOuAtualizarFolhaPagamento(@Valid @RequestBody GerarFolhaRequest request) {
        // Lançar exceção se funcionário não for encontrado
        Funcionario funcionario = funcionarioService.buscarPorMatricula(request.matricula());
        if (funcionario == null) {
            throw new ResourceNotFoundException("Funcionário com matrícula " + request.matricula() + " não encontrado.");
        }

        FolhaDePagamento folha = folhaPagamentoRepository
                .findByMatriculaAndMesReferencia(request.matricula(), request.mesReferencia())
                .orElse(new FolhaDePagamento());

        if (folha.getItens() != null) {
            folha.getItens().clear();
        }

        folha.setMatricula(funcionario.getIdPessoa());
        folha.setMesReferencia(request.mesReferencia());
        folha.setDiasFalta(request.diasFalta());
        folha.setSalarioBruto(funcionario.getSalarioBruto());

        List<ItemFolha> itensCalculados = calculadoraService.calcularFolhaCompleta(funcionario, request.diasFalta());
        itensCalculados.forEach(i -> i.setFolhaDePagamento(folha));

        BigDecimal totalProventos = itensCalculados.stream()
                .filter(i -> "PROVENTO".equals(i.getTipo()))
                .map(ItemFolha::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDescontos = itensCalculados.stream()
                .filter(i -> "DESCONTO".equals(i.getTipo()))
                .map(ItemFolha::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        folha.setItens(itensCalculados);
        folha.setTotalProvento(totalProventos);
        folha.setTotalDesconto(totalDescontos);
        folha.setSalarioLiquido(totalProventos.subtract(totalDescontos));

        FolhaDePagamento folhaSalva = folhaPagamentoRepository.save(folha);

        System.out.println("Folha gerada/atualizada para matrícula: " + request.matricula());

        return ResponseEntity.ok(folhaSalva);
    }

    @GetMapping
    public ResponseEntity<List<FolhaDePagamento>> buscarTodas() {
        return ResponseEntity.ok(folhaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolhaDePagamento> buscarPorId(@PathVariable Integer id) {
        return folhaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folha de pagamento com id " + id + " não encontrada."))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/funcionario/{matricula}")
    public ResponseEntity<List<FolhaDePagamento>> buscarPorFuncionario(@PathVariable Integer matricula) {
        List<FolhaDePagamento> folhas = folhaPagamentoRepository.findAll()
                .stream()
                .filter(f -> f.getMatricula().equals(matricula))
                .toList();

        if (folhas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma folha encontrada para matrícula: " + matricula);
        }

        return ResponseEntity.ok(folhas);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarFolha(@PathVariable Integer id) {
        if (!folhaPagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Folha de pagamento com id " + id + " não encontrada.");
        }
        folhaPagamentoRepository.deleteById(id);
        System.out.println("Folha deletada: " + id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/dias-falta")
    @Transactional
    public ResponseEntity<FolhaDePagamento> atualizarDiasFalta(@PathVariable Integer id, @RequestBody int diasFalta) {
        FolhaDePagamento folha = folhaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folha de pagamento com id " + id + " não encontrada."));
        folha.setDiasFalta(diasFalta);
        FolhaDePagamento folhaAtualizada = folhaPagamentoRepository.save(folha);
        System.out.println("Dias de falta atualizados para folha: " + id);
        return ResponseEntity.ok(folhaAtualizada);
    }
}
