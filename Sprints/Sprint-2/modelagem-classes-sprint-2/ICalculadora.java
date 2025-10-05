import java.math.BigDecimal;

public interface ICalculadora {
    FolhaDePagamento calcularFolha(double salarioMinimo, double grauRisco, String descricao);
}
