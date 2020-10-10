package fred;

import java.util.EnumSet;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

public class Generate {
    //EnversSchemaGenerator
    public static void main(String[] args) throws Exception {

        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", PostgreSQLDialect.class.getName())
                        .build());
    
        metadata.addAnnotatedClass(PvsContribution.class);
        metadata.addAnnotatedClass(MaybeAmount.class);
    
        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.buildMetadata();
        SchemaExport export = new SchemaExport();
    
        export.setDelimiter(";");
        String filename = "ddl_" + PostgreSQLDialect.class.getSimpleName().toLowerCase() + ".sql";
        export.setOutputFile(filename);
        export.setFormat(true);
    
        //can change the output here
        EnumSet<TargetType> enumSet = EnumSet.of(TargetType.STDOUT);
        export.execute(enumSet, SchemaExport.Action.CREATE, metadataImplementor);
    }
}
