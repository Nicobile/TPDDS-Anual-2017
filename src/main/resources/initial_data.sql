use database
go

declare @temp as int
select @temp = count(1) from sys.schemas where name = 'dds'

if @temp = 0 
begin
    exec ('create SCHEMA dds')
    print 'El schema dds fue creado correctamente'
end 
else 
print 'EL schema dds ya existe'
go