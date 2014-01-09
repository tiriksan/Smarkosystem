package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import spring.ov13.domene.Bruker;

/**
 * @author HJ
 */
public class TestDB {
    private UtilsBean dbUtils = new UtilsBean();
    private Database db = dbUtils.getDb();
    private Connection con = db.getForbindelse();
    
    @Test
    public void test_registrerBruker() throws SQLException {
        Bruker b = new Bruker("luksky@hist.no", "Luke", "Skywalker", 3, "");

        boolean erBrukerRegistrert = dbUtils.registrerBruker(b);
        assert (erBrukerRegistrert);

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
    public void test_registrerEksisterendeBruker() throws SQLException {
        Bruker b = new Bruker("luksky@hist.no", "Luke", "Skywalker", 3, "");
        boolean erBrukerRegistrert = dbUtils.registrerBruker(b);
        assert (!erBrukerRegistrert);
    }
    
    @Test
    public void test_oppdaterBruker() throws SQLException {
        Bruker b = new Bruker("luksky@hist.no", "Darth", "Vader", 3, "");

        boolean erPersonOppdatert = dbUtils.oppdaterBruker(b);
        assert (erPersonOppdatert);

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
        Bruker b = new Bruker("mamma@hist.no", "Moder", "Jord", 1, "");
        boolean erBrukerOppdatert = dbUtils.oppdaterBruker(b);
        assert (!erBrukerOppdatert);
    }
    
}
