package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Fag;

/**
 * @author HJ
 */
public class TestDB {

    private Connection con;

    @Before
    public void getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "aXJff+6e");

        con = DriverManager.getConnection("jdbc:://mysql.aitel.hist.no:3306/14-ing2-t5", connectionProps);

        System.out.println("Connected to database");
    }
    
    @Test
    public void test_registrerBruker() throws SQLException {
        UtilsBean databaseUtils = new UtilsBean();
        Bruker b = new Bruker("luksky@hist.no", "Luke", "Skywalker", 3, "");

        boolean erPersonRegistrert = databaseUtils.registrerBruker(b);
        assert (erPersonRegistrert);

        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM bruker WHERE brukernavn='luksky@hist.no'");
        String brukernavn = null;
        String fornavn = null;
        String etternavn = null;
        int brukertype = -1;
        String passord = null;

        while (res.next()) {
            brukernavn = res.getString("brukernavn");
            fornavn = res.getString("fornavn");
            etternavn = res.getString("etternavn");
            brukertype = res.getInt("brukertype");
            passord = res.getString("passord");
        }

        assertEquals(b.getBrukernavn(), brukernavn);
        assertEquals(b.getFornavn(), fornavn);
        assertEquals(b.getEtternavn(), etternavn);
        assertEquals(b.getBrukertype(), brukertype);
        assertEquals(b.getPassord(), passord);
    }
    
    @Test
    public void test_oppdaterBruker() {
        
    }
    
    
}
