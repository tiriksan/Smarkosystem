package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import spring.ov13.domene.Bruker;

/**
 * @author HJ
 */
public class TestDB {
    private EmbeddedDatabase db;

    @Before
    public void settOpp() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        db = builder.setType(EmbeddedDatabaseType.DERBY).addScript("resources/SQL/SKS2.sql").build();
    }

    @Test
    public void test_registrerBruker() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker b = new Bruker("luksky@hist.no", "Luke", "Skywalker", 3, "");
        boolean erBrukerRegistrert = database.registrerBruker(b);
        assert (erBrukerRegistrert);
        
        Connection con = database.getForbindelse();
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
    public void test_registrerEksisterendeBruker() {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker b = new Bruker("luksky@hist.no", "Luke", "Skywalker", 3, "");
        boolean erBrukerRegistrert = database.registrerBruker(b);
        assert (!erBrukerRegistrert);

    }

    @Test
    public void test_oppdaterBruker() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker b = new Bruker("luksky@hist.no", "Darth", "Vader", 3, "");

        boolean erPersonOppdatert = database.oppdaterBruker(b);
        assert (erPersonOppdatert);

        Connection con = database.getForbindelse();
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
    public void test_oppdaterBrukerSomIkkeEksisterer() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker b = new Bruker("mamma@hist.no", "Moder", "Jord", 1, "");
        boolean erBrukerOppdatert = database.oppdaterBruker(b);
        assert (!erBrukerOppdatert);
    }

    
    public void rivNed() {
        db.shutdown();
    }
    
}
