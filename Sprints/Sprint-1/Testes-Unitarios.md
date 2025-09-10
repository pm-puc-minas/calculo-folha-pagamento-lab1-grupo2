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
| **N1** | Verificar o calculo para salário cujo base de cálculo de enquadra na primeira faixa da tabela, resultando em isenção de imposto. | Salário Bruto: 2000; Desconto INSS: 160,47; Dependentes: 0 | Salário Base: 1839,53; Base de Calculo: 1839,53; O valor na faixa até R$1903,98, que é insenta. | ? |
| **N2** | Valida o cálculo do IRRF para salário que se enquadra na terceira faixa da tabela, considerando a dedução do dependente. | Salario: 3500; Desconto NSS: 281,62; Dependentes: 1 | Salario base: 3218,38; Base de Calculo: 3028,79; Alicota da 3a faixa(15%): 454,32; Subtrai dedução: 99,52; Valor do desconto do IRRF: 99,52 | ? |
| **N3** | Valida um calculo complexo do IRRF para um sálario na 5a faixa, incluindo múltiplas deduções. | Salario bruto: 7507,49; Desconto: 877,24; Dependentes: 2 | Dedução total: 1256,42; Base de Calculo: 651,07; Alicota 5a faixa: 1719,04; Subtrai dedução da faica: 849,68: Valor do desconto: R%849,68 | ? |

## 3. Cálculo Insalubridade
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Verifica se o o cálculo de insalubridade está correto para o grau de risco "Baixo", que corresponde a 10% do salário mínimo. | Salário: 1380,60; Grau de risco: Baixo (10%) | Valor do adicional: 138,06  | ? |
| **N2** |  Verifica se o o cálculo de insalubridade está correto para o grau de risco "Medio", que corresponde a 20% do salário mínimo. | Salário: 1380,60; Grau de risco: Baixo (20%) | Valor do adicional: 276,12 | ? |
| **N3** |  Verifica se o o cálculo de insalubridade está correto para o grau de risco "Alto", que corresponde a 40% do salário mínimo. | Salário: 1380,60; Grau de risco: Baixo (40%) | Valor do adicional: 552,24 | ? |


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
