import java.math.BigDecimal;

public class CalculoInsalubridade implements ICalculadora {

    @Override
    public FolhaDePagamento calcularFolha(double salarioMinimo, double grauRisco, String descricao) {
        FolhaDePagamento folha = new FolhaDePagamento();
        
        BigDecimal adicional = BigDecimal.valueOf(salarioMinimo * grauRisco);
        
        folha.setDescricao(descricao);
        folha.setValor(adicional);
        return folha;
    }
    
    public BigDecimal calcularAdicional(double salarioMinimo, double grauRisco) {
        return BigDecimal.valueOf(salarioMinimo * grauRisco);
    }
}
