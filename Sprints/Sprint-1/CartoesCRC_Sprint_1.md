# **Cartões CRC (Classe – Responsabilidade – Colaborador)** 

## **CalculadoraFolhaPagamento**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Orquestrar o processo completo de cálculo da folha. | `Funcionario` |
| Delegar o cálculo de proventos e descontos para classes especializadas. | `JornadaDeTrabalho` |
| Montar o objeto final FolhaDePagamento. | `FolhaDePagamento`, `CalculoINSS`, `CalculoIRRF`, `CalculoPericulosidade`, `CalculoInsalubridade`, `CalculoBeneficios`, `CalculoFGTS` |

## **Funcionario**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Armazenar informações pessoais e contratuais. | `Pessoa` |
| Conhecer seu salário bruto e cargo. | `JornadaDeTrabalho` |
| Conhecer seus dependentes e se possui adicionais. | `Dependente` |


## **JornadaDeTrabalho**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Armazenar a carga horária diária e semanal. | Nenhum |
| Calcular a jornada mensal. | Nenhum |

## **CalculoINSS**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor de desconto do INSS com base na tabela progressiva. | Nenhum |


## **CalculoFGTS**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do FGTS. | Nenhum |


## **CalculoIRRF**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor de desconto de IRRF. | `CalculoINSS` |
| Conhecer o valor do INSS para dedução da base de cálculo. |`CalculoINSS` |

## **CalculoPericulosidade**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do adicional de periculosidade. | Nenhum |

## **CalculoInsalubridade**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do adicional de insalubridade. | Nenhum |

## **CalculoBeneficios**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do vale-transporte e vale-alimentação. | Nenhum |

## **GestaoDeAcesso**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Validar a autenticidade das credenciais de um usuário. | `UsuarioRepository` |

## ****LoginController**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Receber as credenciais do usuário. |`GestaoDeAcesso` |
| Iniciar o processo de autenticação. | |

## **RelatorioService**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Formatar e exibir o relatório da folha de pagamento. | `FolhaDePagamento`, `Funcionario`  | 