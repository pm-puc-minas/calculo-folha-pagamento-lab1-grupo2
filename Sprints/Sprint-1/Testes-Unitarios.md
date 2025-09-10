# Documentação de Testes Unitários - Sistema de Folha de Pagamento

## 1. Cálculo INSS
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N2** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N3** | [desc] | [dados de entrada] | [resultado esperado] | ? |

## 2. Cálculo IRRF
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N2** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N3** | [desc] | [dados de entrada] | [resultado esperado] | ? |

## 3. Cálculo Insalubridade
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N2** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N3** | [desc] | [dados de entrada] | [resultado esperado] | ? |

## 4. Cálculo Periculosidade
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Funcionário com direito a periculosidade | Salário: R$ 2.500,00, Apto: true | Adicional: R$ 750,00 (30% do salário) | ? |
| **N2** | Funcionário sem direito a periculosidade | Salário: R$ 3.000,00, Apto: false | Adicional: R$ 0,00 | ? |
| **N3** | Periculosidade com salário mínimo | Salário: R$ 1.380,60, Apto: true | Adicional: R$ 414,18 (30% do salário) | ? |

## 5. Cálculo Benefícios
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Vale alimentação para mês padrão | Vale diário: R$ 24,00, Dias úteis: 22 | Vale total: R$ 528,00 | ? |
| **N2** | Vale transporte menor que 6% do salário | Salário: R$ 3.000,00, Vale recebido: R$ 150,00 | Desconto: R$ 150,00 (valor integral) | ? |
| **N3** | Vale transporte maior que 6% do salário | Salário: R$ 2.000,00, Vale recebido: R$ 300,00 | Desconto: R$ 120,00 (6% do salário) | ? |

## 6. Cálculo FGTS
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | FGTS para salário médio | Salário bruto: R$ 3.000,00 | FGTS: R$ 240,00 (8% do salário) | ? |
| **N2** | FGTS para salário mínimo | Salário bruto: R$ 1.380,60 | FGTS: R$ 110,45 (8% do salário) | ? |
| **N3** | FGTS para salário alto | Salário bruto: R$ 7.500,00 | FGTS: R$ 600,00 (8% do salário) | ? |

## 7. Jornada de Trabalho
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N2** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N3** | [desc] | [dados de entrada] | [resultado esperado] | ? |

## 8. Acesso ao Banco de Dados
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N2** | [desc] | [dados de entrada] | [resultado esperado] | ? |
| **N3** | [desc] | [dados de entrada] | [resultado esperado] | ? |

---

### Status
- ? Pendente
- ✅ Passou
- ❌ Falhou
- 🔄 Em execução
