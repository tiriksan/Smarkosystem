package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.sql.DataSource;
import spring.ov13.domene.*;

public class DatabaseForTesting {

    private String dbNavn;
    private String dbUser;
    private String dbPswrd;
    private Connection forbindelse;

    private DataSource datasource;

    private final String sqlSelectAlleBrukere = "SELECT * FROM bruker ORDER BY etternavn";
    private final String sqlSelectAlleHovedbrukertyper = "SELECT * FROM bruker WHERE hovedbrukertype =? ORDER BY etternavn";
    private final String sqlSelectBruker = "SELECT * FROM bruker WHERE brukernavn =?";
    private final String sqlInsertBruker = "INSERT INTO bruker values(?,?,?,?,?)";
    private final String sqlUpdateBruker = "UPDATE bruker SET fornavn=?, etternavn=?, hovedbrukertype=?, passord=? WHERE brukernavn=?";
    private final String sqlendrePassord = "UPDATE bruker SET passord=? WHERE brukernavn=?";
    private final String sqlSelectAlleFag = "SELECT * FROM emne ORDER BY emnekode";
    private final String sqlSelectFag = "SELECT * FROM emne WHERE emnekode =?";
    private final String sqlInsertFag = "INSERT INTO emne VALUES(?,?,?)";
    private final String sqlUpdateFag = "UPDATE emne SET emnenavn=?, emnekode=? WHERE emnekode=?";
    private final String sqlSelectBrukereIEmne = "SELECT brukernavn, fornavn, etternavn, passord, hovedbrukertype "
            + "FROM bruker LEFT JOIN emne_bruker USING (brukernavn) WHERE emnekode =? ORDER BY etternavn";
    private final String sqlSelectBrukertypeIEmne = "SELECT brukernavn, fornavn, etternavn, passord, hovedbrukertype "
            + "FROM bruker LEFT JOIN emne_bruker USING (brukernavn) WHERE emnekode =? AND brukertype=? ORDER BY etternavn";
    private final String sqlInsertBrukerIEmne = "INSERT INTO emne_bruker VALUES(?,?,?)";
    private final String sqlSelectØving = "SELECT * FROM oving WHERE ovingsnummer=? AND emnekode =?";
    private final String sqlInsertØving = "INSERT INTO oving VALUES(?,?,?,?)";
    private final String sqlUpdateØving = "UPDATE oving SET ovingsnummer =?, emnekode =?, gruppeid=?, obligatorisk=? WHERE ovingsnummer =? AND emnekode=?";
    private final String sqlSelectØvingerIEmne = "SELECT * FROM oving WHERE emnekode=?";
    private final String sqlCountØvinger = "SELECT COUNT(ovingsnummer) as telling FROM oving WHERE emnekode =?";
    private final String sqlDeleteØvinger = "DELETE * WHERE id < ? AND id> ?";
    private final String sqlgetKravGruppe = "Select * from kravgruppe where emnekode =?";
    private final String sqlInsertKravgruppe = "INSERT INTO kravgruppe VALUES(DEFAULT, ?, ?)";
    private final String sqlSelectBrukerHentPassord = "SELECT * FROM bruker WHERE brukernavn=?";
    private final String sqlSelectFageneTilBruker = "select * from emne a, emne_bruker b WHERE b.brukernavn = ? AND a.emnekode = b.emnekode";
    private final String sqlSelectFagKoAktiv = "SELECT * FROM ko WHERE emnekode = ?";
    private final String sqlUpdateFagKoAktiv = "UPDATE ko SET aktiv = ? WHERE emnekode = ?";
    private final String sqlSelectAlleInnleggFraEmnekode = "SELECT * FROM køinnlegg WHERE aktiv = 1";
    private final String sqlSelectAlleBrukereIInnlegg = "SELECT * FROM brukere_i_innlegg WHERE innleggsid = ?";
    private final String sqlErBrukerIFag = "SELECT * FROM emne_bruker WHERE brukernavn= ? AND emnekode= ?";
    private final String sqlInsertKø = "INSERT INTO ko VALUES(?,?,?)";
    private final String sqlInsertKøInnlegg = "INSERT INTO koinnlegg VALUES(?,DEFAULT,?,?,?,?,?,?,?,?,?)";
    private final String sqlSelectpaabrukertype = "SELECT * FROM bruker WHERE fornavn=? AND etternavn =? and hovedbrukertype=?";
    private final String sqlUpdateKøinnleggHjelpBruker = "UPDATE koinnlegg SET hjelp=? WHERE innleggsid=?";
    private final String sqlSelectDistinctBygg = "SELECT DISTINCT bygg FROM lokasjon WHERE emnekode = ?";
    private final String sqlSelectDistinctEtasje = "SELECT DISTINCT etasje FROM lokasjon WHERE emnekode = ? AND bygg = ?";
    private final String sqlSelectDistinctRom = "SELECT DISTINCT rom FROM lokasjon WHERE emnekode = ? AND bygg = ? AND etasje = ?";
    private final String sqlSelectDistinctBord = "SELECT DISTINCT bord FROM lokasjon WHERE emnekode = ? AND bygg = ? AND etasje = ? AND rom = ?";
    private final String sqlSelectBrukereiFag = "Select * from emne_bruker where emnekode =? ORDER by brukertype DESC";
    private final String sqlInsertOvingerGodkjent = "INSERT INTO godkjente_ovinger VALUES(?,?,?,?)";
    private final String sqlDeleteOvingerGodkjent = "DELETE * WHERE emnekode = ? AND brukernavn = ? AND ovingsnummer = ?";
    private final String sqlSelectInnleggFraID = "SELECT * FROM køinnlegg WHERE innleggsid = ?";
    private final String sqlSelectOvingIInnlegg = "SELECT * FROM øvinger_i_innlegg WHERE innleggsid = ? AND BRUKERNAVN = ?";

    /*    public DatabaseForTesting(String dbNavn, String dbUser, String dbPswrd) {
     this.dbNavn = dbNavn;
     this.dbUser = dbUser;
     this.dbPswrd = dbPswrd;
     }
     */
    public DatabaseForTesting(DataSource ds) {
        datasource = ds;
    }
    /*
     public DatabaseForTesting() {
     }
     */

    protected Connection getForbindelse() throws SQLException {
        return datasource.getConnection();
    }

    private void åpneForbindelse() throws Exception {
        try {

            //Class.forName("com.mysql.jdbc.Driver");
            // org.apache.derby.jdbc.
            //  Class.forName("org.apache.derby.jdbc.ClientDriver");
            //forbindelse = DriverManager.getConnection(dbNavn, dbUser, dbPswrd);
            forbindelse = datasource.getConnection();
            System.out.println("Databaseforbindelse opprettet");
        } catch (SQLException e) {
            Opprydder.skrivMelding(e, "KonstruktÃ¸ren");
            Opprydder.lukkForbindelse(forbindelse);
        }
    }

    private void lukkForbindelse() {
        System.out.println("Lukker databaseforbindelsen");
        Opprydder.lukkForbindelse(forbindelse);
    }

    public synchronized boolean registrerBruker(Bruker bruker) {
        boolean ok = false;
        System.out.println("registrerBruker()");
        PreparedStatement psInsertBruker = null;
        bruker.setPassord(java.util.UUID.randomUUID().toString().substring(0, 10));

        try {
            åpneForbindelse();
            psInsertBruker = forbindelse.prepareStatement(sqlInsertBruker);
            psInsertBruker.setString(1, bruker.getBrukernavn());
            psInsertBruker.setString(2, bruker.getFornavn());
            psInsertBruker.setString(3, bruker.getEtternavn());
            System.out.println(bruker.getPassord());
            psInsertBruker.setString(4, bruker.md5(bruker.getPassord()));
            psInsertBruker.setInt(5, bruker.getBrukertype());

            int i = psInsertBruker.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertBrukerIEmne);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized Bruker getBruker(String brukernavn) {
        Bruker b = null;
        ResultSet res;
        System.out.println("getBruker()");
        PreparedStatement psSelectBruker = null;

        try {
            åpneForbindelse();
            psSelectBruker = forbindelse.prepareStatement(sqlSelectBruker);
            res = psSelectBruker.executeQuery();
            while (res.next()) {
                b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectBruker);
        }
        lukkForbindelse();
        return b;
    }

    public synchronized boolean registrerBrukere(ArrayList<Bruker> brukere) {
        boolean ok = false;
        System.out.println("registrerBrukere()");
        PreparedStatement psInsertBrukere = null;

        try {
            åpneForbindelse();
            for (int i = 0; i < brukere.size(); i++) {
                brukere.get(i).setPassord(java.util.UUID.randomUUID().toString().substring(0, 10));
                psInsertBrukere = forbindelse.prepareStatement(sqlInsertBruker);
                psInsertBrukere.setString(1, brukere.get(i).getBrukernavn());
                psInsertBrukere.setString(2, brukere.get(i).getFornavn());
                psInsertBrukere.setString(3, brukere.get(i).getEtternavn());
                psInsertBrukere.setString(4, brukere.get(i).md5(brukere.get(i).getPassord()));
                psInsertBrukere.setInt(5, brukere.get(i).getBrukertype());

                int j = psInsertBrukere.executeUpdate();
                if (j > 0) {
                    ok = true;
                }
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerBrukere()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertBrukerIEmne);
        }
        lukkForbindelse();
        return ok;
    }

    public ArrayList<Bruker> getAlleBrukere() {
        System.out.println("getAlleBrukere()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Bruker> brukerListe = null;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectAlleBrukere);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Bruker b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                if (brukerListe == null) {
                    brukerListe = new ArrayList<Bruker>();
                }
                brukerListe.add(b);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleBrukere()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleBrukere - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return brukerListe;
    }

    public synchronized boolean oppdaterBruker(Bruker bruker) {
        boolean ok = false;
        System.out.println("oppdaterBruker()");
        PreparedStatement psUpdateBruker = null;

        try {
            åpneForbindelse();
            psUpdateBruker = forbindelse.prepareStatement(sqlUpdateBruker);
            psUpdateBruker.setString(1, bruker.getFornavn());
            psUpdateBruker.setString(2, bruker.getEtternavn());
            psUpdateBruker.setInt(3, bruker.getBrukertype());
            psUpdateBruker.setString(4, bruker.getPassord());
            psUpdateBruker.setString(5, bruker.getBrukernavn());
            int i = psUpdateBruker.executeUpdate();
            if (i > 0) {
                ok = true;
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psUpdateBruker);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean endrePassord(Bruker bruker) {
        boolean ok = false;
        System.out.println("endrePassord()");
        PreparedStatement psEndrePassord = null;

        try {
            åpneForbindelse();
            psEndrePassord = forbindelse.prepareStatement(sqlendrePassord);
            psEndrePassord.setString(2, bruker.getBrukernavn());
            psEndrePassord.setString(1, bruker.getPassord());
            int i = psEndrePassord.executeUpdate();
            if (i > 0) {
                ok = true;
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "endrePassord()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "endrePassord - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psUpdateBruker);
        }
        lukkForbindelse();
        return ok;

    }

    //fag metoder //
    public synchronized boolean registrerEmne(Emne fag) {
        boolean ok = false;
        System.out.println("registrerEmne()");
        PreparedStatement psInsertFag = null;

        try {
            åpneForbindelse();
            psInsertFag = forbindelse.prepareStatement(sqlInsertFag);
            psInsertFag.setString(1, fag.getEmnekode());
            psInsertFag.setString(2, fag.getEmnenavn());
            psInsertFag.setString(3, fag.getØvingsbeskrivelse());

            int i = psInsertFag.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertKravgruppe);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized Emne getFag(String fagkode) {
        Emne f = null;
        ResultSet res;
        System.out.println("getFag()");
        PreparedStatement psSelectFag = null;

        try {
            åpneForbindelse();
            psSelectFag = forbindelse.prepareStatement(sqlSelectFag);
            psSelectFag.setString(1, fagkode);
            res = psSelectFag.executeQuery();
            while (res.next()) {
                f = new Emne(res.getString("emnekode"), res.getString("emnenavn"), res.getString("øvingsbeskrivelse"));
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectFag);
        }
        lukkForbindelse();
        return f;
    }

    public ArrayList<Emne> getAlleFag() {
        System.out.println("getAlleFag()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Emne> fagListe = null;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectAlleFag);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Emne f = new Emne(res.getString("emnenavn"), res.getString("emnekode"), res.getString("øvingsbeskrivelse"));
                if (fagListe == null) {
                    fagListe = new ArrayList<Emne>();
                }
                fagListe.add(f);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return fagListe;
    }

    public synchronized boolean oppdaterEmne(Emne emne, String emnekode) {
        boolean ok = false;
        System.out.println("oppdaterFag()");
        PreparedStatement psUpdateFag = null;

        try {
            åpneForbindelse();
            psUpdateFag = forbindelse.prepareStatement(sqlUpdateFag);
            psUpdateFag.setString(1, emne.getEmnenavn());
            psUpdateFag.setString(2, emne.getEmnekode());
            psUpdateFag.setString(3, emnekode);
            int i = psUpdateFag.executeUpdate();
            if (i > 0) {
                ok = true;
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateFag);
        }
        lukkForbindelse();
        return ok;
    }

    // øving //
    public synchronized boolean registrerØving(Øving øving) {
        boolean ok = false;
        System.out.println("registrerØving()");
        PreparedStatement psInsertØving = null;
        try {

            åpneForbindelse();

            psInsertØving = forbindelse.prepareStatement(sqlInsertØving);

            psInsertØving.setInt(1, øving.getØvingsnr());
            psInsertØving.setString(2, øving.getEmnekode());
            psInsertØving.setInt(3, øving.getGruppeid());
            psInsertØving.setBoolean(4, øving.getObligatorisk());

            int i = psInsertØving.executeUpdate();
            if (i > 0) {
                ok = true;
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerØving()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerØving - ikke sqlfeil");

        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertArbeidskrav);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean oppdaterØving(Øving øving, int øvingsnr, String emnekode) {
        boolean ok = false;
        System.out.println("oppdaterØving()");
        PreparedStatement psUpdateØving = null;

        try {
            åpneForbindelse();
            psUpdateØving = forbindelse.prepareStatement(sqlUpdateØving);
            psUpdateØving.setInt(1, øving.getØvingsnr());
            psUpdateØving.setString(2, øving.getEmnekode());
            psUpdateØving.setInt(3, øving.getGruppeid());
            psUpdateØving.setBoolean(4, øving.getObligatorisk());
            psUpdateØving.setInt(5, øvingsnr);
            psUpdateØving.setString(6, emnekode);

            int i = psUpdateØving.executeUpdate();
            if (i > 0) {
                ok = true;
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateØving);
        }
        lukkForbindelse();
        return ok;
    }

    // emne_bruker //
    public synchronized boolean leggTilBrukerIEmne(Emne emne, Bruker bruker, int brukertype) {
        boolean ok = false;
        int brukertypeIEmne = brukertype;
        System.out.println("leggTilBrukerIEmne()");
        PreparedStatement psInsertBrukerIEmne = null;
        if (brukertype < 0 || brukertype > 4) {
            brukertypeIEmne = bruker.getBrukertype();
        }

        try {
            åpneForbindelse();
            psInsertBrukerIEmne = forbindelse.prepareStatement(sqlInsertBrukerIEmne);
            psInsertBrukerIEmne.setString(1, emne.getEmnekode());
            psInsertBrukerIEmne.setString(2, bruker.getBrukernavn());
            psInsertBrukerIEmne.setInt(3, brukertypeIEmne);

            int i = psInsertBrukerIEmne.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "leggTilBrukerIEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "leggTilBrukerIEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertBrukerIEmne);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean leggTilBrukereIEmner(ArrayList<Emne> emner, ArrayList<Bruker> bruker) {
        boolean ok = false;
        int i = 0;

        System.out.println("leggTilBrukerIEmner()");
        PreparedStatement psInsertBrukerIEmne = null;

        try {
            åpneForbindelse();
            psInsertBrukerIEmne = forbindelse.prepareStatement(sqlInsertBrukerIEmne);

            for (int j = 0; j < emner.size(); j++) {
                for (int k = 0; k < bruker.size(); k++) {
                    psInsertBrukerIEmne.setString(1, emner.get(j).getEmnekode());
                    psInsertBrukerIEmne.setString(2, bruker.get(k).getBrukernavn());
                    psInsertBrukerIEmne.setInt(3, bruker.get(k).getBrukertype());
                    i += psInsertBrukerIEmne.executeUpdate();
                }
            }

            if (i == bruker.size() * emner.size()) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "leggTilBrukereIEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "leggTilBrukereIEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertBrukereIEmne);
        }
        lukkForbindelse();
        return ok;
    }

    public int getBrukertypeiEmne(String brukernavn, String emnekode) {
        PreparedStatement psSelectBrukerTypeIEmne = null;
        ResultSet res;
        int brukertype = 0;
        try {
            åpneForbindelse();
            psSelectBrukerTypeIEmne = forbindelse.prepareStatement("SELECT brukertype FROM emne_bruker WHERE emnekode= ? AND brukernavn = ?");
            psSelectBrukerTypeIEmne.setString(1, emnekode);
            psSelectBrukerTypeIEmne.setString(2, brukernavn);
            res = psSelectBrukerTypeIEmne.executeQuery();
            while (res.next()) {
                brukertype = res.getInt("brukertype");
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getBrukertypeiEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getBrukertypeiEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectBrukerTypeIEmne);
        }
        lukkForbindelse();
        return brukertype;
    }

    public ArrayList<Bruker> getAlleBrukereAvBrukertype(int brukertype) {
        System.out.println("getAlleBrukereAvBrukertype()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Bruker> fagListe = new ArrayList<Bruker>();
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement("SELECT * FROM bruker WHERE hovedbrukertype = ? ORDER BY etternavn");

            psSelectAlle.setInt(1, brukertype);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Bruker f = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                fagListe.add(f);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleBrukereAvBrukertype()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleBrukereAvBrukertype - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return fagListe;
    }

    // KRAVGRUPPE //
    public synchronized boolean registrerKravgruppe(Kravgruppe kg) {
        boolean ok = false;
        System.out.println("registrerKravgruppe()");
        PreparedStatement psInsertKravgruppe = null;

        try {
            åpneForbindelse();
            psInsertKravgruppe = forbindelse.prepareStatement(sqlInsertKravgruppe);
            psInsertKravgruppe.setString(1, kg.getEmnekode());
            psInsertKravgruppe.setInt(2, kg.getAntallgodkj());

            int i = psInsertKravgruppe.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerKravgruppe()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerKravgruppe - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertKravgruppe);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized ArrayList<Kravgruppe> getKravGruppertilEmne(String emnekode) {
        System.out.println("hent kravgrupper");
        PreparedStatement psSelectKravGruppe = null;
        int gruppeID;

        ArrayList<Kravgruppe> krav = new ArrayList<Kravgruppe>();
        ResultSet res;
        try {
            åpneForbindelse();
            psSelectKravGruppe = forbindelse.prepareStatement(sqlgetKravGruppe);
            psSelectKravGruppe.setString(1, emnekode);
            res = psSelectKravGruppe.executeQuery();

            while (res.next()) {
                Kravgruppe k = new Kravgruppe(res.getInt("gruppeID"), emnekode, res.getInt("antall"));
                krav.add(k);

            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerArbeidskrav()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerArbeidskrav - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertKravgruppe);
        }

        return krav;
    }

    public ArrayList<String> getInfoTilBruker(String brukernavn) {
        System.out.println("getAlleFag()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<String> returnen = new ArrayList<String>();
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectBrukerHentPassord);

            psSelectAlle.setString(1, brukernavn);
            res = psSelectAlle.executeQuery();
            while (res.next()) {

                if (returnen.size() != 0) {
                    returnen = new ArrayList<String>();
                }
                returnen.add(res.getString("brukernavn"));
                returnen.add(res.getString("passord"));
                returnen.add(res.getString("fornavn"));
                returnen.add(res.getString("etternavn"));
                returnen.add(res.getString("hovedbrukertype"));

            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return returnen;
    }

    public ArrayList<Emne> getFageneTilBruker(String brukernavn) {
        System.out.println("getFageneTilBruker()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Emne> fagListe = null;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectFageneTilBruker);
            psSelectAlle.setString(1, brukernavn);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Emne f = new Emne(res.getString("emnenavn"), res.getString("emnekode"), res.getString("øvingsbeskrivelse"));
                if (fagListe == null) {
                    fagListe = new ArrayList<Emne>();
                }
                fagListe.add(f);
                System.out.println("Jeg hentet fag");
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFageneTilBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFageneTilBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return fagListe;
    }

    public boolean getFagKoAktiv(String emnekode) {
        System.out.println("getFagKoAktiv()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        boolean returnen = false;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectFagKoAktiv);

            psSelectAlle.setString(1, emnekode);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                returnen = res.getBoolean("aktiv");
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFagKoAktiv()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFagKoAktiv - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return returnen;
    }

    public boolean updateFagKoAktiv(String emnekode, boolean aktiv) {
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;

        boolean ok = false;
        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlUpdateFagKoAktiv);

            psUpdateFagKoAktiv.setBoolean(1, aktiv);
            psUpdateFagKoAktiv.setString(2, emnekode);

            int i = psUpdateFagKoAktiv.executeUpdate();

            if (i > 0) {
                ok = true;
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFagKoAktiv()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFagKoAktiv - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateFagKoAktiv);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean registrerKø(int køId, String emnekode, boolean aktiv) {
        boolean ok = false;
        System.out.println("registrerKø()");
        PreparedStatement psInsertKø = null;

        try {
            åpneForbindelse();
            psInsertKø = forbindelse.prepareStatement(sqlInsertKø);
            psInsertKø.setInt(1, køId);
            psInsertKø.setString(2, emnekode);
            psInsertKø.setBoolean(3, aktiv);

            int i = psInsertKø.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerKø()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerKø - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertArbeidskrav);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean registrerKøInnlegg(int id, int kønummer, String brukernavn, Plassering lokasjon, String kommentar) {
        boolean ok = false;
        System.out.println("registrerKøInnlegg()");
        PreparedStatement psInsertKø = null;

        try {
            åpneForbindelse();
            psInsertKø = forbindelse.prepareStatement(sqlInsertKøInnlegg);
            psInsertKø.setInt(1, id);
            psInsertKø.setInt(2, kønummer);
            psInsertKø.setString(3, brukernavn);
            psInsertKø.setString(4, lokasjon.getBygning());
            psInsertKø.setInt(5, lokasjon.getEtasje());
            psInsertKø.setString(6, lokasjon.getRom());
            psInsertKø.setInt(7, lokasjon.getBord());
            psInsertKø.setString(8, null);
            psInsertKø.setString(9, lokasjon.getEmnekode());
            psInsertKø.setString(10, kommentar);

            int i = psInsertKø.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerKø()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerKø - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psInsertArbeidskrav);
        }
        lukkForbindelse();
        return ok;
    }

    public boolean erBrukerIFag(String brukernavn, String emnekode) {
        System.out.println("erBrukerIFag()");
        PreparedStatement psErBrukerIFag = null;
        ResultSet res;
        boolean brukerErIFag = false;
        try {
            åpneForbindelse();
            psErBrukerIFag = forbindelse.prepareStatement(sqlErBrukerIFag);

            psErBrukerIFag.setString(1, brukernavn);
            psErBrukerIFag.setString(2, emnekode);
            res = psErBrukerIFag.executeQuery();
            brukerErIFag = res.next();
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "erBrukerIFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "erBrukerIFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psErBrukerIFag);
        }
        lukkForbindelse();
        return brukerErIFag;
    }

    public ArrayList<Bruker> getBrukereIInnlegg(int id) {
        PreparedStatement psSelectBrukere = null;
        ArrayList<Bruker> brukere = new ArrayList();
        try {
            åpneForbindelse();
            psSelectBrukere = forbindelse.prepareStatement(sqlSelectAlleBrukereIInnlegg);
            psSelectBrukere.setInt(1, id);
            ResultSet rs = psSelectBrukere.executeQuery();
            while (rs.next()) {
                Bruker bruker = getBruker(rs.getString("brukernavn"));
                //bruker.setBrukernavn(rs.getString("brukernavn"));
                //bruker.setFornavn(rs.getString("fornavn"));
                //bruker.setEtternavn(rs.getString("etternavn"));
                brukere.add(bruker);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFagKoAktiv()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFagKoAktiv - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectBrukere);
        }
        lukkForbindelse();
        return brukere;

    }

    public ArrayList<Innlegg> getFulleInnleggTilKo(String emnekode) {
        System.out.println("getFulleInnleggTilKo()");
        PreparedStatement psSelectAlle = null;
        PreparedStatement dobbel = null;
        ResultSet res;
        ResultSet res2;
        ArrayList<Innlegg> returnen = new ArrayList<Innlegg>();
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectAlleInnleggFraEmnekode);

            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Innlegg innlegg = new Innlegg();

                innlegg.setBrukere(null);
                innlegg.setHjelp(null);
                innlegg.setKønummer(res.getInt("kønummer"));
                innlegg.setOvinger(null);
                innlegg.setTid(0);
                innlegg.setId(res.getInt("innleggsid"));

                // KOMMENTER UT, HENT UT EKTE DIN LATSABB 
                ArrayList<Øving> ovinger = new ArrayList<Øving>();
                Øving øving1 = new Øving();
                øving1.setEmnekode(emnekode);
                øving1.setØvingsnr(1);
                ovinger.add(øving1);

                if (res.getString("eier").equals("petterlu@stud.hist.no")) {

                    Øving øving2 = new Øving();
                    øving2.setEmnekode(emnekode);
                    øving2.setØvingsnr(2);
                    ovinger.add(øving2);
                }
                Øving øving3 = new Øving();
                øving3.setEmnekode(emnekode);
                øving3.setØvingsnr(3);
                ovinger.add(øving3);

                Øving øving4 = new Øving();
                øving4.setEmnekode(emnekode);
                øving4.setØvingsnr(4);

                ovinger.add(øving4);
                // KOMMENTER UT, HENT UT EKTE DIN LATSABB - OVER AND OUT 

                innlegg.setEier(null);
                Plassering plass = new Plassering();
                plass.setBygning("MAIN HALL");
                plass.setEtasje(2);
                plass.setRom("1408");
                innlegg.setPlass(plass);
                ArrayList<ArrayList<Øving>> alleov = new ArrayList<ArrayList<Øving>>();
                alleov.add(ovinger);
                innlegg.setOvinger(alleov);
                returnen.add(innlegg);

            }

            /*
            
             PreparedStatement trippel = forbindelse.prepareStatement(sqlSelectBruker);
             trippel.setString(1, res.getString("brukernavn"));
             ResultSet set = trippel.executeQuery();
             while(set.next()){
             Bruker bruker = new Bruker();
             bruker.setBrukernavn(set.getString("brukernavn"));
             bruker.setBrukertype(set.getInt("hovedbrukertype"));
             bruker.setFornavn(set.getString("fornavn"));
             bruker.setEtternavn(set.getString("etternavn"));
             innlegg.setEier(bruker);
             }
             */
            for (int i = 0; i < returnen.size(); i++) {
                dobbel = forbindelse.prepareStatement(sqlSelectAlleBrukereIInnlegg);
                System.out.println("Henter fra ID: " + returnen.get(i).getId());
                dobbel.setInt(1, returnen.get(i).getId());
                res2 = dobbel.executeQuery();
                ArrayList<Bruker> brukerne = new ArrayList<Bruker>();
                while (res2.next()) {
                    System.out.println("Dermed henter vi ut navn: " + res2.getString("brukernavn"));
                    PreparedStatement trippel = forbindelse.prepareStatement(sqlSelectBruker);
                    trippel.setString(1, res2.getString("brukernavn"));
                    ResultSet set = trippel.executeQuery();

                    while (set.next()) {
                        Bruker bruker = new Bruker();
                        bruker.setBrukernavn(set.getString("brukernavn"));
                        bruker.setBrukertype(set.getInt("hovedbrukertype"));
                        bruker.setFornavn(set.getString("fornavn"));
                        bruker.setEtternavn(set.getString("etternavn"));
                        brukerne.add(bruker);
                    }

                }
                returnen.get(i).setBrukere(brukerne);
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFulleInnleggTilKo()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFulleInnleggTilKo - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        System.out.println("Returnerer liste med størrelse: " + returnen.size());
        return returnen;
    }

    //Endre hvem som hjelper et køinnlegg. Null dersom køinnlegget ikke får hjelp
    public boolean setKøinnleggHjelpBruker(Bruker bruker, int køinnleggid) {
        System.out.println("setBrukerHjelperKøinnlegg()");
        //set hjelp(brukvernavn)= bruker where køinnleggid = køinnleggid
        PreparedStatement psUpdateKøinleggHjelpBruker = null;
        boolean ok = false;
        try {
            åpneForbindelse();
            psUpdateKøinleggHjelpBruker = forbindelse.prepareStatement(sqlUpdateKøinnleggHjelpBruker);
            if (bruker != null) {
                psUpdateKøinleggHjelpBruker.setString(1, bruker.getBrukernavn());
            } else {
                psUpdateKøinleggHjelpBruker.setString(1, null);
            }
            psUpdateKøinleggHjelpBruker.setInt(2, køinnleggid);
            int i = psUpdateKøinleggHjelpBruker.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "updateKøinnleggHjelpBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "updateKøinnleggHjelpBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateKøinleggHjelpBruker);
        }
        lukkForbindelse();
        return ok;
    }

    public String[] getUnikeBygg(String emnekode) {
        System.out.println("getUnikeBygg()");
        PreparedStatement psGetUnikeBygg = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psGetUnikeBygg = forbindelse.prepareStatement(sqlSelectDistinctBygg);

            psGetUnikeBygg.setString(1, emnekode);

            res = psGetUnikeBygg.executeQuery();
            while (res.next()) {
                mid.add(res.getString("bygg"));
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getUnikeBygg()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getUnikeBygg - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psGetUnikeBygg);
        }
        lukkForbindelse();
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public String[] getUnikeEtasjer(String emnekode, String bygg) {
        System.out.println("getUnikeEtasjer()");
        PreparedStatement psGetUnikeEtasjer = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psGetUnikeEtasjer = forbindelse.prepareStatement(sqlSelectDistinctEtasje);

            psGetUnikeEtasjer.setString(1, emnekode);
            psGetUnikeEtasjer.setString(2, bygg);

            res = psGetUnikeEtasjer.executeQuery();
            while (res.next()) {
                mid.add(res.getString("etasje"));
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getUnikeEtasjer()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getUnikeEtasjer - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psGetUnikeEtasjer);
        }
        lukkForbindelse();
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public String[] getUnikeRom(String emnekode, String bygg, String etasje) {
        System.out.println("getUnikeRom()");
        PreparedStatement psGetUnikeRom = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psGetUnikeRom = forbindelse.prepareStatement(sqlSelectDistinctRom);
            int etasje2 = Integer.parseInt(etasje);
            psGetUnikeRom.setString(1, emnekode);
            psGetUnikeRom.setString(2, bygg);
            psGetUnikeRom.setInt(3, etasje2);

            res = psGetUnikeRom.executeQuery();
            while (res.next()) {
                mid.add(res.getString("rom"));
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getUnikeRom()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getUnikeRom - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psGetUnikeRom);
        }
        lukkForbindelse();
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public String[] getUnikeBord(String emnekode, String bygg, String etasje, String rom) {
        System.out.println("getUnikeBord()");
        PreparedStatement psGetUnikeBord = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psGetUnikeBord = forbindelse.prepareStatement(sqlSelectDistinctBord);
            int etasje2 = Integer.parseInt(etasje);
            psGetUnikeBord.setString(1, emnekode);
            psGetUnikeBord.setString(2, bygg);
            psGetUnikeBord.setInt(3, etasje2);
            psGetUnikeBord.setString(4, rom);

            res = psGetUnikeBord.executeQuery();
            while (res.next()) {
                mid.add(res.getString("bord"));
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getUnikeBord()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getUnikeBord - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psGetUnikeBord);
        }
        lukkForbindelse();
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public ArrayList<ArrayList<Øving>> getØvingerTilBrukereIInnlegg(int innleggsID, ArrayList<Bruker> brukere) {
        ArrayList<ArrayList<Øving>> øvinger = new ArrayList();
        PreparedStatement psSqlSelectØvingerIInnlegg;
        ResultSet res;
        try {
            åpneForbindelse();
            psSqlSelectØvingerIInnlegg = forbindelse.prepareStatement(sqlSelectOvingIInnlegg);
            psSqlSelectØvingerIInnlegg.setInt(1, innleggsID);
            for (int i = 0; i < brukere.size(); i++) {
                psSqlSelectØvingerIInnlegg.setString(2, brukere.get(i).getBrukernavn());
                res = psSqlSelectØvingerIInnlegg.executeQuery();
                øvinger.add(new ArrayList());
                while (res.next()) {
                    Øving ov = new Øving();
                    ov.setEmnekode(res.getString("emnekode"));
                    ov.setØvingsnr(res.getInt("øvingsnummer"));
                    øvinger.get(i).add(ov);
                }
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "øvingerIInnlegg()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "øvingerIInnlegg - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psUpdateBruker);
        }

        return øvinger;
    }

    public synchronized boolean setInnOvingerGodkjent(String godkjenner, String emnekode, String brukernavn, int øving) {
        boolean ok = false;
        System.out.println("setOvingerGodkjent()");
        PreparedStatement psSetInnOvingerGodkjent = null;

        try {
            åpneForbindelse();
            int j = 0;

            psSetInnOvingerGodkjent = forbindelse.prepareStatement(sqlInsertOvingerGodkjent);
            psSetInnOvingerGodkjent.setString(1, godkjenner);
            psSetInnOvingerGodkjent.setString(2, emnekode);
            psSetInnOvingerGodkjent.setString(3, brukernavn);
            psSetInnOvingerGodkjent.setInt(4, øving);

            j += psSetInnOvingerGodkjent.executeUpdate();
            if (j > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "SetInnOvingerGodkjent()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "SetInnOvingerGodkjent - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psSetInnOvingerGodkjent);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean fjernOvingerGodkjent(String emnekode, String brukernavn, ArrayList<Integer> øvinger) {
        boolean ok = false;
        System.out.println("fjernOvingerGodkjent()");
        PreparedStatement psfjernOvingerGodkjent = null;

        try {
            åpneForbindelse();
            int j = 0;
            for (int i = 0; i < øvinger.size(); i++) {

                psfjernOvingerGodkjent = forbindelse.prepareStatement(sqlDeleteOvingerGodkjent);
                psfjernOvingerGodkjent.setString(1, emnekode);
                psfjernOvingerGodkjent.setString(2, brukernavn);
                psfjernOvingerGodkjent.setInt(3, øvinger.get(i));

                j += psfjernOvingerGodkjent.executeUpdate();
            }
            if (j >= øvinger.size()) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "fjernOvingerGodkjent()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "fjernOvingerGodkjent - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psfjernOvingerGodkjent );
        }
        lukkForbindelse();
        return ok;
    }

}
