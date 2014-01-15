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
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import spring.ov13.domene.*;

/**
 * @author HJ
 */
public class TestDB {
    //private EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    private EmbeddedDatabase db; // = builder.setType(EmbeddedDatabaseType.DERBY).addDefaultScripts().build();

    @Before
    public void settOpp() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        db = builder.setType(EmbeddedDatabaseType.DERBY).addDefaultScripts().build();
    }

    @Test
    public void test_registrerBruker() throws SQLException, ClassNotFoundException {
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
            brukertype = res.getInt("hovedbrukertype");
            passord = res.getString("passord");
        }

        assertEquals(b.getBrukernavn(), brukernavn);
        assertEquals(b.getFornavn(), fornavn);
        assertEquals(b.getEtternavn(), etternavn);
        assertEquals(b.getBrukertype(), brukertype);
        assertEquals(b.md5(b.getPassord()), passord);
    }

    @Test
    public void test_registrerEksisterendeBruker() {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker b = new Bruker("anasky@hist.no", "Anakin", "Skywalker", 3, "NoSoup4U");
        boolean erBrukerRegistrert = database.registrerBruker(b);
        assert (!erBrukerRegistrert);

    }

    @Test
    public void test_oppdaterBruker() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker b = new Bruker("anasky@hist.no", "Darth", "Vader", 2, "");

        boolean erPersonOppdatert = database.oppdaterBruker(b);
        assert (erPersonOppdatert);

        Connection con = database.getForbindelse();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM bruker WHERE brukernavn='anasky@hist.no'");
        String brukernavn = null;
        String fornavn = null;
        String etternavn = null;
        int brukertype = -1;
        String passord = null;

        while (res.next()) {
            brukernavn = res.getString("brukernavn");
            fornavn = res.getString("fornavn");
            etternavn = res.getString("etternavn");
            brukertype = res.getInt("hovedbrukertype");
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
        Emne emne = new Emne("TDAT2001", "Testing & Ballefranserier", "Alle baller må være godkjent");
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
        Emne emne = new Emne("TDAT3003", "3D-Programmering", "Må ha godkjent 5 av 10 øvinger.");
        boolean erEmneRegistrert = database.registrerEmne(emne);
        assert (!erEmneRegistrert);
    }
    
    @Test
    public void test_oppdaterEmne() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("IFUD1337", "Tåmalerier i Moderne Tid", "Ingen krav.");

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
        Emne emne = new Emne("IKKE1337", "Finnes Ikke", "Ingen øvinger");
        boolean erEmneOppdatert = database.oppdaterEmne(emne, emne.getEmnekode());
        assert (!erEmneOppdatert);
    }

    @Test
    public void test_leggTilBrukerIEmne() throws SQLException {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Emne emne = new Emne("TDAT3008", "Java EE og distribuerte systemer", "Må ha godtkjent 3 av de første fem øvingene, og minst 2 av de 4 siste.");
        Bruker bruker = new Bruker("anasky@hist.no", "Anakin", "Skywalker", 3, "");
        boolean erBrukerLagtTil = database.leggTilBrukerIEmne(emne, bruker, 3);
        assert (erBrukerLagtTil);
        
        Connection con = database.getForbindelse();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM emne_bruker WHERE emnekode='TDAT3008' AND brukernavn='anasky@hist.no'");
        String emnekode = null;
        String brukernavn = null;
        int brukertype = -1;

        while (res.next()) {
            emnekode = res.getString("emnekode");
            brukernavn = res.getString("brukernavn");
            brukertype = res.getInt("brukertype");
        }
        assertEquals(emne.getEmnekode(), emnekode);
        assertEquals(bruker.getBrukernavn(), brukernavn);
        assertEquals(3, brukertype);
    }
    
    @Test
    public void test_registrerBrukerPåIkkeEksisterendeEmne() {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker bruker = new Bruker("meg@hist.no", "Meg", "OgBareMeg", 0, "");
        Emne emne = new Emne("IKKE2009", "BS", "Ingen krav");
        boolean brukerRegistrertIEmne = database.leggTilBrukerIEmne(emne, bruker, 3);
        assert (!brukerRegistrertIEmne);
    }
    
    @Test()
    public void test_leggTilEksisterendeBrukerPåEksisterendeFag() {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Bruker bruker = new Bruker("anasky@hist.no", "Anakin", "Skywalker", 0, "NoSoup4U");
        Emne emne = new Emne("TDAT3003", "3D-programmering", "Må ha godkjent 5 av 10 øvinger.");
        boolean duplikat = database.leggTilBrukerIEmne(emne, bruker, bruker.getBrukertype());
        assert(!duplikat);
    }
    
    @Test
    public void loggInn() {
        DatabaseForTesting database = new DatabaseForTesting(db);
        String md5Passord = "7c3daa31f887c333291d5cf04e541db5";
        Bruker bruker = new Bruker("test@hist.no", "Herman", "Jensen", 0, "");
        boolean registrert = database.registrerBruker(bruker);
        assert(registrert && bruker.getPassord().equals(md5Passord));
    }
    
    @Test
    public void registrerKravgruppe() {
        DatabaseForTesting database = new DatabaseForTesting(db);
        Kravgruppe kg = new Kravgruppe("TDAT3003", 2);
        boolean registrert = database.registrerKravgruppe(kg);
        assert(registrert);
    }
    
    @After
    public void rivNed() {
        db.shutdown();
    }
    
}
