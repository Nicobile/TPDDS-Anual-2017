<html>
    <head>
        <title> Grupo 12 </title>
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="../css/starter-template.css">
         
    </head>
    
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"></a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                                              
                         <li><a href="/empresas">Obtener Empresas</a></li>
                         <li><a href="/indicador">Crear indicador</a></li>
                          <li><a href="/indicadores">Calcular indicador</a></li>
                          <li><a href="/metodologia">Crear metodologia</a></li>   
                          <li><a href="/metodologias">Aplicar metodologia</a></li>  
                          <li><a href="/login">Login</a></li>  
                         
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
        <script src="../js/jquery.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <div class="container">
            <#include "${templateName}">
        </div>
    </body>
</html>