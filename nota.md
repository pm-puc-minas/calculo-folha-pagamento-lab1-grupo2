Nota Final: 3.5


Execução do Trabalho: Deduzi pontos pelo erro durante os testes e no cálculo da Folha de Pagamento:
```
[ERROR] Tests run: 2, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.657 s <<< FAILURE! -- in com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.CalculoFolhaDePagamentoApplicationTests
[ERROR] com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.CalculoFolhaDePagamentoApplicationTests.testCalculoFolhaComFaltasEInsalubridade -- Time elapsed: 0.008 s <<< ERROR!
com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.InvalidDataException: O cargo do funcionário é obrigatório.
        at com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService.validarDadosFuncionario(FuncionarioService.java:239)
        at com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService.salvarFuncionario(FuncionarioService.java:133)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:360)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380)
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:728)
        at com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService$$SpringCGLIB$$0.salvarFuncionario(<generated>)
        at com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.CalculoFolhaDePagamentoApplicationTests.testCalculoFolhaComFaltasEInsalubridade(CalculoFolhaDePagamentoApplicationTests.java:32)
        at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)

[INFO]
[INFO] Results:
[INFO]
[ERROR] Failures:
[ERROR]   TestCalculoFGTS.deveCalcularFgtsParaSalarioValido:47 O tipo do item deveria ser 'Provento'. ==> expected: <Provento> but was: <PROVENTO>
[ERROR]   RelatorioServiceTests.buscarTodasAsFolhas_DeveRetornarListaComSucesso:83 expected: <1> but was: <0>
[ERROR] Errors:
[ERROR]   CalculoFolhaDePagamentoApplicationTests.testCalculoFolhaComFaltasEInsalubridade:32 » InvalidData O cargo do funcionário é obrigatório.
[INFO]
[ERROR] Tests run: 17, Failures: 2, Errors: 1, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  36.593 s
[INFO] Finished at: 2025-12-10T11:36:56-03:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.5.3:test (default-test) on project calculo-folha-de-pagamento: There are test failures.
[ERROR]
[ERROR] See /home/paulohdscoelho/projetos/aulas_PUC/trabalho_final/calculo-folha-pagamento-lab1-grupo2/Projeto/calculo-folha-de-pagamento/target/surefire-reports for the individual test results.
[ERROR] See dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
```

Consegui entrar na aplicação, só que o cálculo de IRRF não faz sentido, faz?
![alt text](image.png)