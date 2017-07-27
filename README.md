# consulta_cnpj
É um protótipo desenvolvido em java para consulta de CNPJ.<br>

# Consulta
1 - É realizado uma consulta no https://receitaws.com.br/ que é um serviço privado que disponibiliza consulta de cnpj.<br>
2 - Caso a primeira opção apresente erro ou não retorne resposta é consultado o site http://www.receita.fazenda.gov.br.<br>

# Processamento da resposta
1 - Processando a resposta do https://receitaws.com.br/ é convertido o json em objeto que disponibiliza todas as informações do CNPJ.<br>
2 - Processando a resposta do http://www.receita.fazenda.gov.br é recuperado o html da respota, convertido para texto e as informações
são interpretadas conforme o posição dos campos.<br>
