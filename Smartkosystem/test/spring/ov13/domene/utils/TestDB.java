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
import spring.ov13.domene.Emne;

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
        Bruker b = new Bruker("anasky@hist.no", "Anakin", "Skywalker", 3, "");
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
    
    @Test
    public void test_registrerEmne() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("TDAT2001", "Testing & Ballefranserier");
        boolean erFagRegistrert = database.registrerEmne(emne);
        assert (erFagRegistrert);
        
        Connection con = database.getForbindelse();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM emne WHERE emnekode='TDAT2001'");
        String emnekode = null;
        String emnenavn = null;

        while (res.next()) {
            emnekode = res.getString("emnekode");
            emnenavn = res.getString("emnenavn");
        }
        assertEquals(emne.getEmnekode(), emnekode);
        assertEquals(emne.getEmnenavn(), emnenavn);
    }
    
    @Test
    public void test_registrerEksisterendeEmne() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("TDAT3003", "3D-Programmering");
        boolean erEmneRegistrert = database.registrerEmne(emne);
        assert (!erEmneRegistrert);
    }
    
    @Test
    public void test_oppdaterEmne() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("IFUD1337", "Tåmalerier i Moderne Tid");

        boolean erEmneOppdatert = database.oppdaterEmne(emne, emne.getEmnekode());
        assert (erEmneOppdatert);

        Connection con = database.getForbindelse();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM emne WHERE emnekode='IFUD1337'");
        String emnekode = null;
        String emnenavn = null;

        while (res.next()) {
            emnekode = res.getString("emnekode");
            emnenavn = res.getString("emnenavn");
        }

        assertEquals(emne.getEmnekode(), emnekode);
        assertEquals(emne.getEmnenavn(), emnenavn);
    }
    
    @Test
    public void test_oppdaterEmneSomIkkeEksisterer() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("IKKE1337", "Finnes Ikke");
        boolean erEmneOppdatert = database.oppdaterEmne(emne, emne.getEmnekode());
        assert (!erEmneOppdatert);
    }

    @Test
    public void test_leggTilBrukerIEmne() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("TDAT9090", "Matematikk 2");
        Bruker bruker = new Bruker("grestra@hist.no", "Grethe", "Sandstrak", 3, "");
        boolean erBrukerLagtTil = database.leggTilBrukerIEmne(emne, bruker, 3);
        assert (erBrukerLagtTil);
        
        Connection con = database.getForbindelse();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM emne_bruker WHERE emnekode='TDAT9090' AND brukernavn='grestra@hist.no'");
        String emnekode = null;
        String brukernavn = null;

        while (res.next()) {
            emnekode = res.getString("emnekode");
            brukernavn = res.getString("emnenavn");
        }
        assertEquals(emne.getEmnekode(), emnekode);
        assertEquals(bruker.getBrukernavn(), brukernavn);
    }
    
    
    public void rivNed() {
        db.shutdown();
    }
    
}
