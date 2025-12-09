O ControladorDashboard é uma classe na camada de Negócio (Negocio) do diagrama de classes da arquitetura do sistema financeiro, projetado para gerenciar as operações relacionadas à visualização e cálculo de métricas no dashboard do usuário. Ele segue o padrão de projeto MVC implícito, atuando como controlador que coordena a lógica de negócio para o dashboard, integrando-se a estratégias de cálculo e métricas. Abaixo, descrevo seus atributos, métodos e relações com base nos diagramas fornecidos (considerando tanto a versão anterior quanto a atualizada, onde não há alterações significativas específicas para esse componente, exceto o contexto geral de autenticação integrada).

### Atributos
- **estrategiaCalculo : IEstrategiaCalculo** (implícito pela relação no diagrama): Representa uma referência a uma interface de estratégia de cálculo. Esse atributo permite que o controlador utilize diferentes algoritmos de cálculo de forma dinâmica, sem acoplamento rígido, seguindo o padrão Strategy. Não há outros atributos explícitos listados no diagrama para essa classe.

### Métodos
- **+carregaDadosDashboard() : void**: Responsável por carregar os dados necessários para o dashboard. Esse método coordena a obtenção de informações financeiras (como receitas, despesas e distribuições), utilizando a estratégia de cálculo associada para processar os dados brutos vindos de serviços ou repositórios. Ele provavelmente invoca serviços subjacentes (como ServicoDashboard) para agregar métricas e preparar os dados para a camada de apresentação (ex: TelaDashboard).
- **+registraEstrategia() : void**: Permite registrar ou alterar a estratégia de cálculo em tempo de execução. Isso facilita a variação de algoritmos (ex: cálculo de totais de receitas vs. despesas) sem modificar o código do controlador, promovendo flexibilidade e extensibilidade, conforme nota no diagrama sobre "escolher para variar algoritmos de calculo de totais e posteriror total nova estrategias sem alterar o codigo anterior".

### Relações
- **Com a EstrategiaCalculo (Interface Abstrata e Subclasses)**:
  - Relação principal: Composição ou agregação via o atributo estrategiaCalculo, implementando o padrão Strategy. O ControladorDashboard depende da interface IEstrategiaCalculo para delegar cálculos específicos.
  - Subclasses relacionadas: 
    - **EstrategiaCalculoDespesas**: Uma implementação concreta para calcular totais de despesas, possivelmente focando em filtros por período, cartão ou tipo de lançamento.
    - **EstrategiaCalculoReceitas**: Similar, mas para receitas, permitindo cálculos personalizados como somas, médias ou projeções.
  - No diagrama, há uma seta de dependência ou associação do ControladorDashboard para a EstrategiaCalculo, com nota enfatizando a variação de algoritmos sem impacto no código existente. Isso permite que o método carregaDadosDashboard() invoque métodos da estratégia (ex: calcularTotal()) para processar dados de lançamentos financeiros.

- **Com a MetricaDashboard (Classe de Métricas)**:
  - Relação indireta via serviços: O ControladorDashboard não possui uma associação direta explícita no diagrama, mas interage com MetricaDashboard através do ServicoDashboard, que tem um atributo -metricaDashboard : MetricaDashboard. O controlador usa esse serviço para popular ou consultar instâncias de MetricaDashboard.
  - Atributos da MetricaDashboard (que são preenchidos via cálculos do controlador/estratégia):
    - -totalReceitas : double: Armazena o total de receitas calculadas.
    - -totalDespesas : double: Armazena o total de despesas calculadas.
    - -distribuicaoPorCartoes : Map: Um mapa para distribuição de valores por cartões de crédito (ex: chave como ID do cartão, valor como soma de transações).
  - Métodos da MetricaDashboard (não detalhados no diagrama, mas implícitos): Provavelmente getters e setters para os atributos, além de possíveis métodos como +calcularSaldo() : void para derivar métricas compostas.
  - Fluxo típico: O ControladorDashboard registra a estratégia, carrega dados de repositórios (via serviços como ServicoFinancas ou ServicoCartoes), aplica a estratégia para computar valores e armazena o resultado em uma MetricaDashboard, que é então enviada para a camada de apresentação (ex: TelaDashboard via +acessaDashboard() ou +atualizar()).

### Contexto Geral no Diagrama de Arquitetura
- O ControladorDashboard está posicionado na camada Negocio, abaixo da Apresentacao (onde interage com TelaDashboard) e acima da Dados (via serviços e repositórios como RepositorioFinancas).
- Na versão atualizada do diagrama (com autenticação), há uma integração implícita com componentes como ControladorAutenticacao e ServicoAutenticacao, garantindo que o carregamento de dados do dashboard exija autenticação (ex: via Keycloak), embora não haja setas diretas para o dashboard. Isso alinha com a arquitetura SOA/Microserviços, onde o dashboard pode ser exposto via API em microserviços como "financeiro".
- Não há relações diretas com outros controladores (ex: ControladorCartao ou ControladorFatura), mas indiretamente via fachada (FachadaSistemaFinanceiro) ou serviços compartilhados, permitindo composição de métricas a partir de dados de cartões, lançamentos e faturas.
