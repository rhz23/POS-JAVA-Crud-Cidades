<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRUD Cidades</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <h1>GERENCIAMENTO DE CIDADES</h1>
            <p>UM CRUD PARA CRIAR, ALTERAR, EXCLUIR E LISTAR CIDADES</p>
        </div>

        <#if cidadeAtual??>
            <form action="/alterar" method="post">
            <input type="hidden" name="nomeAtual" value="${(cidadeAtual.nome)!}"/>
            <input type="hidden" name="estadoAtual" value="${(cidadeAtual.estado)!}"/>
                <#else>
                <form action="/criar" method="post">
                    </#if>

        <form action="/criar" method="post">
            <div class="form-group">
                <label for="nome">Cidade:</label>
                <input value="${(cidadeAtual.nome)!}" name="nome" type="text" class="form-control" placeholder="Informe o nome da cidade" id="nome">
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <input value="${(cidadeAtual.estado)!}" name="estado" type="text" class="form-control" placeholder="Informe o estado ao qual a cidade pertence" id="estado">
            </div>
            <#if cidadeAtual??>
                <button type="submit" class="btn btn-warning">CONCLUIR ALTERAÇÃO</button
                    <#else>
                        <button type="submit" class="btn btn-primary">CRIAR</button>
            </#if>
        </form>
        <table class="table table-striped table-hover mt-5">
            <thead class="thead-dark">
            <tr>
                <th>Nome</th>
                <th>Estado</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <#list listaCidades as cidade>
            <tr>
                <td>${cidade.nome}</td>
                <td>${cidade.estado}</td>
                <td>
                    <div class="d-flex d-justify-content-center">
                        <a href="/preparaAlterar?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-warning">ALTERAR</a>
                        <a href="/excluir?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-danger">EXCLUIR</a>
                    </div>
                </td>
            </tr>
            </#list>

            </tbody>
        </table>
    </div>

</body>
</html>