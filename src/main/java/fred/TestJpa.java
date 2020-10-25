package fred;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;

import au.com.superchoice.gridservices.jpa.domain.ContributionMemberAudit;
import au.com.superchoice.gridservices.jpa.domain.ErsEmployerFund;
import au.com.superchoice.gridservices.jpa.domain.ErsEmployerFundHistory;
import au.com.superchoice.gridservices.jpa.domain.PvsContribution;
import au.com.superchoice.gridservices.jpa.domain.PvsDataFile;
import au.com.superchoice.gridservices.jpa.domain.PvsEmployer;
import au.com.superchoice.gridservices.jpa.domain.PvsEmployerEmployee;
import au.com.superchoice.gridservices.jpa.domain.PvsFund;
import au.com.superchoice.gridservices.jpa.domain.PvsInputSource;
import au.com.superchoice.gridservices.jpa.domain.PvsInputSourceEmployer;
import au.com.superchoice.gridservices.jpa.domain.PvsInputSourceFile;
import au.com.superchoice.gridservices.jpa.domain.PvsInputSourceSchedule;
import au.com.superchoice.gridservices.jpa.domain.PvsMatchingException;
import au.com.superchoice.gridservices.jpa.domain.PvsMatchingExceptionMember;
import au.com.superchoice.gridservices.jpa.domain.PvsMember;
import au.com.superchoice.gridservices.jpa.domain.PvsMemberParameter;
import au.com.superchoice.gridservices.jpa.domain.PvsMemberSyncRequest;
import au.com.superchoice.gridservices.jpa.domain.PvsValidationError;


public class TestJpa {
    protected static Class<?>[] entities() {
        return new Class[] {
            PvsContribution.class, PvsInputSource.class, PvsMember.class, PvsEmployerEmployee.class, PvsFund.class, PvsEmployer.class, ContributionMemberAudit.class,
            PvsMemberSyncRequest.class, PvsValidationError.class, PvsInputSourceEmployer.class, PvsInputSourceFile.class, PvsDataFile.class, PvsInputSourceSchedule.class,
            PvsMatchingException.class, PvsMatchingExceptionMember.class, PvsMemberParameter.class, ErsEmployerFundHistory.class, ErsEmployerFund.class
        };
    }
    
    public static void main(String[] args) {
        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitInfo(
            TestJpa.class.getSimpleName()
        );
     
        Map<String, Object> configuration = new HashMap<>();
     
        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo,configuration);

        EntityManager entityManager = null;
        EntityTransaction txn = null;
        try {
            entityManager = emf.createEntityManager();
            txn = entityManager.getTransaction();
            txn.begin();

            List<PvsContribution> entityAList = entityManager.createQuery(
                "SELECT sum(CAST(pc.superGuaranteeAmt AS big_decimal) ), cf.fieldIdentifier, sum(cf.amountAmt) " +
                "from PvsContribution pc " +
                "inner join pc.customFields cf " +
                "left join pc.pvsInputSource pds  " +
    		    "left join pc.pvsMember pm " + 
    		    "left join pds.pvsInputSourceFiles pdsf " + 
                "join  pdsf.pvsDataFile pdf " + 
    		    "where pdf.pvsDataFileId = 671878 " + 
                "group by cf.fieldIdentifier " + 
                "order by cf.fieldIdentifier "

            ).getResultList();

            List<PvsContribution> entityAList = entityManager.createQuery("Select c from PvsContribution c").getResultList();
            // entityAList.forEach(System.out::println);

            // Object o = entityManager.createQuery("Select c.customFields['fred1'] from PvsContribution c").getSingleResult();
            // Object customFields = entityManager.createQuery("SELECT customFields FROM PvsContribution c JOIN c.customFields customFields").getResultList();
            // Object amountAmt = entityManager.createQuery("SELECT customFields.amountAmt FROM PvsContribution c JOIN c.customFields customFields").getResultList();
            // Object sum1 = entityManager.createQuery("SELECT key(customFields), value(customFields) FROM PvsContribution c JOIN c.customFields customFields where key(customFields) = 'fred1'").getResultList();

            // Object fred1_sum = entityManager.createQuery("SELECT sum(customFields.amountAmt) FROM PvsContribution c JOIN c.customFields customFields where  key(customFields) = 'fred1'").getSingleResult();
            // Object fred2_sum = entityManager.createQuery("SELECT sum(customFields.amountAmt) FROM PvsContribution c JOIN c.customFields customFields where  key(customFields) = 'fred2'").getSingleResult();
            // Object fred14_sum = entityManager.createQuery("SELECT sum(customFields.amountAmt) FROM PvsContribution c JOIN c.customFields customFields where key(customFields) = 'fred14'").getSingleResult();

            Object r = entityManager.createQuery("select pf.pvsFundId, "

            + "sum(fred1.amountAmt) as fred1, "
            + "sum(fred2.amountAmt) as fred2, "
            + "sum(fred3.amountAmt) as fred3, "
            + "sum(fred4.amountAmt) as fred4, "

            + "pc.superGuaranteeAmt, pc.awardProductivityAmt, pc.personalContrAmt, pc.salSacrificeAmt, "
    		+ "pc.voluntaryAmt, pc.spouseContrAmt, pc.childContrAmt, pc.oth3rdPartyContrAmt, pc.defBenMemPreTaxContr, pc.defBenPostTaxContr, pc.defBenEmpContr "
            + "from au.com.superchoice.gridservices.jpa.domain.PvsFund pf "
            + "left join pf.pvsMembers pm "
            + "left join pm.pvsContributions pc "
            + "left join pc.pvsInputSource pic "
            + "left join pic.pvsInputSourceFiles psfs "
            + "left join psfs.pvsDataFile pdf "

            + "left join pc.customFields fred1 on key(fred1) = 'fred1' "
            + "left join pc.customFields fred2 on key(fred2) = 'fred2' "
            + "left join pc.customFields fred3 on key(fred3) = 'fred3' "
            + "left join pc.customFields fred4 on key(fred4) = 'fred4' "

            + "where pc.pvsMember.pvsMemberId = pm.pvsMemberId "
            + "and pm.pvsFund.pvsFundId = pf.pvsFundId "
            + "and pdf.pvsDataFileId=671878 "
            + "group by pf.pvsFundId, pc.superGuaranteeAmt, pc.awardProductivityAmt, pc.personalContrAmt, pc.salSacrificeAmt, "
            + "pc.voluntaryAmt, pc.spouseContrAmt, pc.childContrAmt, pc.oth3rdPartyContrAmt, pc.defBenMemPreTaxContr, pc.defBenPostTaxContr, pc.defBenEmpContr "
            
            // + ",fred1.amountAmt " 
            // + ",fred2.amountAmt " 
            // + ",fred3.amountAmt " 
            // + ",fred4.amountAmt  "
            
            ).getResultList();
            
            txn.commit();
        } catch (RuntimeException e) {
            if ( txn != null && txn.isActive()) txn.rollback();
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    
    protected static PersistenceUnitInfoImpl persistenceUnitInfo(String name) {
        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl(
            name, entityClassNames(), properties()
        );
     
        String[] resources = resources();
        if (resources != null) {
            persistenceUnitInfo.getMappingFileNames().addAll(
                Arrays.asList(resources)
            );
        }
     
        return persistenceUnitInfo;
    }    

    protected static String[] resources() {
        return null;
    }

    protected static Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
         
        DataSource dataSource = newDataSource();
         
        if (dataSource != null) {
            properties.put(
                "hibernate.connection.datasource",
                dataSource
            );
        }
         
        properties.put(
            "hibernate.generate_statistics",
            Boolean.TRUE.toString()
        );
     
        return properties;
    }

    protected static List<String> entityClassNames() {
        return Arrays.asList(entities())
        .stream()
        .map(Class::getName)
        .collect(Collectors.toList());
    }

    protected static DataSource newDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource() ;  
        // ds.setServerName( "portal-fred.devint.superchoice.com.au" );  
        // ds.setDatabaseName( "postgres" );   
        // ds.setPortNumber(2880);
        ds.setUser( "postgres" );       
        ds.setPassword( "docker" ); 
        ds.setURL("jdbc:postgresql://portal-fred.devint.superchoice.com.au:2880/postgres?currentSchema=empis_sys");
        return ds;
     }
      
}
