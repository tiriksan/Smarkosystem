package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.Plassering;
import spring.ov13.domene.Øving;
import spring.ov13.domene.Innlegg;
import spring.ov13.domene.Kravgruppe;

public class Database {

    private String dbNavn;
    private String dbUser;
    private String dbPswrd;
    private Connection forbindelse;

    private final String sqlSelectAlleBrukere = "SELECT * FROM bruker ORDER BY etternavn";
    private final String sqlSelectAlleHovedbrukertyper = "SELECT * FROM bruker WHERE hovedbrukertype =? ORDER BY etternavn";
    private final String sqlSelectBruker = "SELECT * FROM bruker WHERE brukernavn =?";
    private final String sqlSelectBrukerPaNavn = "SELECT concat(fornavn,' ',etternavn) AS navn, bruker.* FROM bruker WHERE concat(fornavn,' ',etternavn) LIKE ?";
    private final String sqlInsertBruker = "INSERT INTO bruker values(?,?,?,?,?)";
    private final String sqlUpdateBruker = "UPDATE bruker SET fornavn=?, etternavn=?, hovedbrukertype=?, passord=? WHERE brukernavn=?";
    private final String sqlendrePassord = "UPDATE bruker SET passord=? WHERE brukernavn=?";
    private final String sqlSelectAlleFag = "SELECT * FROM emne ORDER BY emnekode";
    private final String sqlUpdageOvingsbeskrivelse = "UPDATE emne set beskrivelse=? where emnekode=?";
    private final String sqlSelectFag = "SELECT * FROM emne WHERE emnekode =?";
    private final String sqlInsertFag = "INSERT INTO emne VALUES(?,?,?)";
    private final String sqlUpdateFag = "UPDATE emne SET emnenavn=?, beskrivelse=? WHERE emnekode=?";
    private final String sqlSelectEmnePaabokstav = "SELECT concat(emnekode,'-',emnenavn) AS navn, emne.* FROM emne WHERE concat(emnekode,'-',emnenavn) LIKE ?";
    private final String sqlSelectBrukereIEmne = "SELECT brukernavn, fornavn, etternavn, passord, hovedbrukertype "
            + "FROM bruker LEFT JOIN emne_bruker USING (brukernavn) WHERE emnekode =? ORDER BY etternavn";
    private final String sqlSelectBrukertypeIEmne = "SELECT brukernavn, fornavn, etternavn, passord, hovedbrukertype "
            + "FROM bruker LEFT JOIN emne_bruker USING (brukernavn) WHERE emnekode =? AND brukertype=? ORDER BY etternavn";
    private final String sqlInsertBrukerIEmne = "INSERT INTO emne_bruker VALUES(?,?,?)";
    private final String sqlSelectØving = "SELECT * FROM øving WHERE øvingsnummer=? AND emnekode =?";
    private final String sqlInsertØving = "INSERT INTO øving VALUES(?,?,?,?)";
    private final String sqlUpdateØving = "UPDATE øving SET øvingsnr =?, emnekode =?, gruppeid=?, obligatorisk=? WHERE øvingsnr =? AND emnekode=?";
    private final String sqlSelectØvingerIEmne = "SELECT * FROM øving WHERE emnekode=?";
    private final String sqlCountØvinger = "SELECT COUNT(øvingsnummer) as telling FROM øving WHERE emnekode =?";
    private final String sqlDeleteØvinger = "DELETE * WHERE id < ? AND id> ?";
    private final String sqlInsertKravgruppe = "INSERT INTO arbeidskrav VALUES(?,?,?)";
    private final String sqlgetKravGruppe = "Select * from kravgruppe where emnekode =?";
    private final String sqlSelectBrukerHentPassord = "SELECT * FROM bruker WHERE brukernavn=?";
    private final String sqlSelectFageneTilBruker = "select * from emne a, emne_bruker b WHERE b.brukernavn = ? AND a.emnekode = b.emnekode";
    private final String sqlSelectFagKoAktiv = "SELECT * FROM kø WHERE emnekode = ?";
    private final String sqlUpdateFagKoAktiv = "UPDATE kø SET aktiv = ? WHERE emnekode = ?";
    private final String sqlSelectAlleInnleggFraEmnekode = "SELECT * FROM køinnlegg";
    private final String sqlSelectAlleBrukereIInnlegg = "SELECT * FROM brukere_i_innlegg WHERE innleggsid = ?";
    private final String sqlErBrukerIFag = "SELECT * FROM emne_bruker WHERE brukernavn= ? AND emnekode= ?";
    private final String sqlInsertFagLaerer = "INSERT into emne_bruker VALUES(?,?,?)";
    private final String sqlInsertKø = "INSERT INTO kø VALUES(?,?,?)";
    private final String sqlInsertKøInnlegg = "INSERT INTO køinnlegg VALUES(?,DEFAULT,?,?,?,?,?,?,?,?,?)";
    private final String sqlSelectpaabrukertype = "SELECT * FROM bruker WHERE fornavn=? AND etternavn =? and hovedbrukertype=?";
    private final String sqlUpdateKøinnleggHjelpBruker = "UPDATE køinnlegg SET hjelp=? WHERE innleggsid=?";
    private final String sqlSelectDistinctBygg = "SELECT DISTINCT bygg FROM lokasjon WHERE emnekode = ?";
    private final String sqlSelectDistinctEtasje = "SELECT DISTINCT etasje FROM lokasjon WHERE emnekode = ? AND bygg = ?";
    private final String sqlSelectDistinctRom = "SELECT DISTINCT rom FROM lokasjon WHERE emnekode = ? AND bygg = ? AND etasje = ?";
    private final String sqlSelectDistinctBord = "SELECT DISTINCT bord FROM lokasjon WHERE emnekode = ? AND bygg = ? AND etasje = ? AND rom = ?";
    private final String sqlSelectBrukereiFag = "Select * from emne_bruker, bruker where emnekode =? AND bruker.brukernavn = emne_bruker.brukernavn ORDER by bruker.etternavn ASC";
    private final String sqlInsertOvingerGodkjent = "INSERT INTO godkjente_øvinger VALUES(?,?,?,?)";
    private final String sqlDeleteOvingerGodkjent = "DELETE From godkjente_øvinger WHERE emnekode = ? AND brukernavn = ? AND øvingsnummer = ?";
    private final String sqlSelectInnleggFraID = "SELECT * FROM køinnlegg WHERE innleggsid = ?";
    private final String sqlSelectOvingIInnlegg = "SELECT * FROM øvinger_i_innlegg WHERE innleggsid = ? AND BRUKERNAVN = ?";
    private final String sqlSelectInnleggFraHjelpEmne = "SELECT * FROM køinnlegg WHERE hjelp = ? AND emnekode = ?";
    private final String sqlDeleteKoInnleggFraID = "DELETE from køinnlegg WHERE innleggsid = ?";
    private final String sqlSjekkOmOvingErGodkjent = "SELECT * FROM godkjente_øvinger WHERE brukernavn = ? AND emnekode = ? AND øvingsnummer = ?";
    private final String sqlSelectStudenterIEmne = "Select * FROM bruker JOIN (emne_bruker) ON (bruker.brukernavn = emne_bruker.brukernavn) WHERE emne_bruker.emnekode = ? AND emne_bruker.brukertype = 1 ORDER BY bruker.etternavn";
    private final String sqlGetGodkjentOvingerForBrukerIEmne = "SELECT B.øvingsnummer, A.øvingsnummer as godkjent from (select * from godkjente_øvinger where brukernavn = ? and emnekode = ?) as A right outer join (select * from `øving` where `øving`.emnekode = ?) AS B on(A.emnekode = B.emnekode and A.øvingsnummer = B.øvingsnummer)";
    private final String sqlSelectAlleUgjorteOvinger = "SELECT * FROM bruker a, øving b WHERE b.emnekode = ? AND a.brukernavn = ? AND NOT EXISTS (select * from godkjente_øvinger c WHERE b.emnekode = c.emnekode AND b.`øvingsnummer` = c.`øvingsnummer` AND a.brukernavn = c.brukernavn)";
    private final String sqlSelectCountOvingerIFag = "SELECT count(*) AS tall FROM øving WHERE emnekode = ?";
    private final String sqlSelectSisteInnleggsid = "SELECT * FROM køinnlegg ORDER BY innleggsid DESC limit 1";
    private final String sqlSelectSisteKo = "SELECT * FROM kø WHERE emnekode = ? ORDER BY kønummer DESC limit 1";
    private final String sqlInsertBrukereIInnlegg = "INSERT INTO brukere_i_innlegg VALUES(?,?)";
    private final String sqlInsertOvingerIInnlegg = "INSERT INTO øvinger_i_innlegg VALUES(?,?, ?, ?)";
    private final String sqlSelectGodkjenteØvingerKravgruppeBruker = "select kravgruppe.gruppeid, A.antallfullført,kravgruppe.antall from kravgruppe left outer join (select gruppeid,count(gruppeid) as antallfullført from godkjente_øvinger natural join øving where brukernavn=? group by gruppeid) as A on(kravgruppe.gruppeid=A.gruppeid) where emnekode=? ORDER BY kravgruppe.gruppeid;";

    public Database(String dbNavn, String dbUser, String dbPswrd) {
        this.dbNavn = dbNavn;
        this.dbUser = dbUser;
        this.dbPswrd = dbPswrd;
    }

    public Database() {
    }

    protected Connection getForbindelse() {
        return forbindelse;
    }

    private void åpneForbindelse() throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            // org.apache.derby.jdbc.
            //  Class.forName("org.apache.derby.jdbc.ClientDriver");
            forbindelse = DriverManager.getConnection(dbNavn, dbUser, dbPswrd);
        } catch (SQLException e) {
            Opprydder.skrivMelding(e, "Konstruktøren");
            Opprydder.lukkForbindelse(forbindelse);
        }
    }

    private void lukkForbindelse() {
        Opprydder.lukkForbindelse(forbindelse);
    }
// Metode for å hente inn bruker søkt på bokstav //

    public ArrayList<Bruker> getBrukerepaabokstav(String bokstav) {
        /**
         * **********************************************************************
         * Denne metoden er tilknyttet søkeboksen for endre bruker * Lagrer en
         * klargjort Sql setning som brukes mot database * Vi lager en ArrayList
         * der vi lagrer Bruker objekter som returneres *
         * ***********************************************************************
         */
        Bruker b = null;
        ResultSet res;
        System.out.println("getBrukerpåbokstav)");
        PreparedStatement psSelectBruker = null;
        ArrayList<Bruker> BrukerePaaNavn = new ArrayList<Bruker>();

        try {
            åpneForbindelse();
            psSelectBruker = forbindelse.prepareStatement(sqlSelectBrukerPaNavn);
            System.out.println("hallais");
            psSelectBruker.setString(1, "%" + bokstav + "%");
            System.out.println("hallais2");

            res = psSelectBruker.executeQuery();
            while (res.next()) {
                System.out.println("hallais3");
                b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                BrukerePaaNavn.add(b);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getBrukerpaabokstav()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectBruker);
        }
        lukkForbindelse();
        return BrukerePaaNavn;

    }

    // Metode for å hente inn emne på søkt på bokstav //
    public ArrayList<Emne> getEmnepaabokstav(String bokstav) {
        /**
         * **********************************************************************
         * Denne metoden er tilknyttet søkeboksen for endre emne* Lagrer en *
         * klargjort Sql setning som brukes mot database. Vi lager en ArrayList*
         * der vi lagrer Emne objekter som returneres *
         * ************************************************************************
         */
        Emne b = null;
        ResultSet res;
        System.out.println("getEmnepåbokstav)");
        PreparedStatement psSelectEmne = null;
        ArrayList<Emne> EmnePaaNavn = new ArrayList<Emne>();

        try {
            åpneForbindelse();
            psSelectEmne = forbindelse.prepareStatement(sqlSelectEmnePaabokstav);
            System.out.println("hallais emne");
            psSelectEmne.setString(1, "%" + bokstav + "%");
            System.out.println("hallais emne2");

            res = psSelectEmne.executeQuery();
            while (res.next()) {
                System.out.println("hallais emne3");
                b = new Emne(res.getString("emnekode"), res.getString("emnenavn"), res.getString("beskrivelse"));
                EmnePaaNavn.add(b);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getEmnepaabokstav()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);

        }
        lukkForbindelse();
        return EmnePaaNavn;

    }

    public ArrayList<Øving> getØvingerIEmnet(String emnekode) {
        Øving øv = null;
        ResultSet res;
        System.out.println("getØvingerIEmnet()");
        PreparedStatement psSelectØving = null;
        ArrayList<Øving> ØvingerIEmnet = new ArrayList<Øving>();
        try {
            åpneForbindelse();
            psSelectØving = forbindelse.prepareStatement(sqlSelectØvingerIEmne);
            psSelectØving.setString(1, emnekode);
            res = psSelectØving.executeQuery();
            while (res.next()) {

                øv = new Øving(res.getInt("øvingsnummer"), res.getString("emnekode"), res.getInt("gruppeid"), res.getBoolean("obligatorisk"));
                ØvingerIEmnet.add(øv);
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
        return ØvingerIEmnet;

    }

    public ArrayList<Bruker> getBrukereIEmnet(String emnekode) {
        Bruker b = null;
        ResultSet res;
        System.out.println("getBrukerIFag()");
        PreparedStatement psSelectBruker = null;
        ArrayList<Bruker> BrukereIFaget = new ArrayList<Bruker>();

        try {
            åpneForbindelse();
            psSelectBruker = forbindelse.prepareStatement(sqlSelectBrukereiFag);
            psSelectBruker.setString(1, emnekode);
            res = psSelectBruker.executeQuery();
            while (res.next()) {
                b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                BrukereIFaget.add(b);
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
        return BrukereIFaget;

    }

    public synchronized boolean registrerBruker(Bruker bruker) {
        boolean ok = false;
        System.out.println("registrerBruker()");
        PreparedStatement psInsertBruker = null;
        PreparedStatement psInsertBrukerifag = null;
        bruker.setPassord(java.util.UUID.randomUUID().toString().substring(0, 10));

        try {
            åpneForbindelse();
            psInsertBruker = forbindelse.prepareStatement(sqlInsertBruker);
            psInsertBruker.setString(1, bruker.getFornavn());
            psInsertBruker.setString(2, bruker.getEtternavn());
            psInsertBruker.setInt(3, bruker.getBrukertype());
            psInsertBruker.setString(4, bruker.md5(bruker.getPassord()));
            psInsertBruker.setString(5, bruker.getBrukernavn());
            psInsertBruker.executeUpdate();

            for (int i = 0; i < bruker.getFagene().size(); i++) {
                psInsertBrukerifag = forbindelse.prepareStatement(sqlInsertBrukerIEmne);
                psInsertBrukerifag.setString(1, bruker.getFagene().get(i).getEmnekode());
                psInsertBrukerifag.setString(2, bruker.getBrukernavn());
                psInsertBrukerifag.setInt(3, bruker.getBrukertype());
                psInsertBrukerifag.executeUpdate();
            }

            int i = psInsertBrukerifag.executeUpdate();
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
            psSelectBruker.setString(1, brukernavn);
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

    public ArrayList<Bruker> getStudenterIEmnet(String emnekode) {
        Bruker b = null;
        ResultSet res;
        System.out.println("getStudenterIEmnet()");
        PreparedStatement psSelectStudenterIEmnet = null;
        ArrayList<Bruker> studenterIEmnet = new ArrayList<Bruker>();

        try {
            åpneForbindelse();
            psSelectStudenterIEmnet = forbindelse.prepareStatement(sqlSelectStudenterIEmne);
            psSelectStudenterIEmnet.setString(1, emnekode);
            res = psSelectStudenterIEmnet.executeQuery();
            while (res.next()) {
                b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                studenterIEmnet.add(b);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getStudenterIEmnet()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getStudenterIEmnet - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectStudenterIEmnet);
        }
        lukkForbindelse();
        return studenterIEmnet;

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
            psUpdateBruker.setString(4, bruker.md5(bruker.getPassord()));
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
        PreparedStatement psInsertLaerer = null;

        try {
            åpneForbindelse();
            psInsertFag = forbindelse.prepareStatement(sqlInsertFag);
            psInsertFag.setString(2, fag.getEmnenavn());
            psInsertFag.setString(1, fag.getEmnekode());
            psInsertFag.setString(3, "");
            for (int i = 0; i < fag.getFaglærer().size(); i++) {
                psInsertLaerer = forbindelse.prepareStatement(sqlInsertFagLaerer);
                psInsertLaerer.setString(1, fag.getEmnekode());
                psInsertLaerer.setString(2, fag.getFaglærer().get(i).getBrukernavn());
                psInsertLaerer.setInt(3, 2);
                psInsertLaerer.executeUpdate();
            }

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
                f = new Emne(res.getString("emnekode"), res.getString("emnenavn"), res.getString("beskrivelse"));
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
                Emne f = new Emne(res.getString("emnenavn"), res.getString("emnekode"), res.getString("beskrivelse"));
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

    // er det heldig å oppdatere en primarykey? 
    public synchronized boolean oppdaterEmne(Emne emne, String beskrivelse) {
        boolean ok = false;
        System.out.println("oppdaterFag()");
        PreparedStatement psUpdateFag = null;

        try {
            åpneForbindelse();
            psUpdateFag = forbindelse.prepareStatement(sqlUpdateFag);
            psUpdateFag.setString(1, emne.getEmnenavn());
            psUpdateFag.setString(2, beskrivelse);
            psUpdateFag.setString(3, emne.getEmnekode());
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
            // Opprydder.lukkSetning(psInsertKravgruppe);
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

    public synchronized boolean setInnOvingerGodkjent(String godkjenner, String emnekode, String brukernavn, ArrayList<Integer> øvinger) {
        boolean ok = false;
        System.out.println("setOvingerGodkjent()");
        PreparedStatement psSetInnOvingerGodkjent = null;

        try {
            åpneForbindelse();
            int j = 0;
            for (int i = 0; i < øvinger.size(); i++) {

                psSetInnOvingerGodkjent = forbindelse.prepareStatement(sqlInsertOvingerGodkjent);
                psSetInnOvingerGodkjent.setString(1, godkjenner);
                psSetInnOvingerGodkjent.setString(2, emnekode);
                psSetInnOvingerGodkjent.setString(3, brukernavn);
                psSetInnOvingerGodkjent.setInt(4, øvinger.get(i));

                j += psSetInnOvingerGodkjent.executeUpdate();
            }
            if (j >= øvinger.size()) {
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

    public synchronized boolean sjekkOmOvingErGodkjent(String brukenavn, String emnekode, int ovingsnr) {
        boolean ok = false;
        System.out.println("sjekkOmOvingErGodkjent");
        PreparedStatement psSjekkOmOvingErGodkjent = null;
        ResultSet res;

        try {
            åpneForbindelse();

            psSjekkOmOvingErGodkjent = forbindelse.prepareStatement(sqlSjekkOmOvingErGodkjent);

            res = psSjekkOmOvingErGodkjent.executeQuery();

            if (res.next()) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "sjekkOmOvingErGodkjent()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "sjekkOmOvingErGodkjent - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psSjekkOmOvingErGodkjent);
        }
        lukkForbindelse();
        return ok;
    }

    public int getAntOvingerIEmne(String emnekode) {
        int ut = 0;
        System.out.println("getAntOvingerIEmne");
        PreparedStatement psGetAntOvingerIEmne = null;
        ResultSet res;

        try {
            åpneForbindelse();

            psGetAntOvingerIEmne = forbindelse.prepareStatement(sqlCountØvinger);
            psGetAntOvingerIEmne.setString(1, emnekode);
            res = psGetAntOvingerIEmne.executeQuery();

            if (res.next()) {
                ut = res.getInt("telling");
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAntOvingerIEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAntOvingerIEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psGetAntOvingerIEmne);
        }
        lukkForbindelse();
        return ut;
    }

    public synchronized int[] getGodkjentOvingerForBrukerIEmne(String brukernavn, String emnekode, int antØving) {
        int[] utTab = new int[antØving];
        System.out.println("getGodkjentOvingerForBrukerIEmne");
        PreparedStatement psGetGodkjentOvingerForBrukerIEmne = null;
        ResultSet res;

        try {
            åpneForbindelse();
            psGetGodkjentOvingerForBrukerIEmne = forbindelse.prepareStatement(sqlGetGodkjentOvingerForBrukerIEmne);
            psGetGodkjentOvingerForBrukerIEmne.setString(1, brukernavn);
            psGetGodkjentOvingerForBrukerIEmne.setString(2, emnekode);
            psGetGodkjentOvingerForBrukerIEmne.setString(3, emnekode);
            res = psGetGodkjentOvingerForBrukerIEmne.executeQuery();
            int i = 0;
            while (res.next()) {
                if (res.getInt("godkjent") > 0) {
                    utTab[i] = 1;
                } else {
                    utTab[i] = 0;
                }
                i++;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getGodkjentOvingerForBrukerIEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getGodkjentOvingerForBrukerIEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            // Opprydder.lukkSetning(psSjekkOmOvingErGodkjent);
        }
        lukkForbindelse();
        return utTab;
    }

    public Innlegg getInnleggFraID(int innleggID) {
        System.out.println("Innleggid: " + innleggID);
        Innlegg innlegg = new Innlegg();
        PreparedStatement psSelectInnlegg = null;
        try {
            åpneForbindelse();
            psSelectInnlegg = forbindelse.prepareStatement(sqlSelectInnleggFraID);
            psSelectInnlegg.setInt(1, innleggID);
            ResultSet rs = psSelectInnlegg.executeQuery();
            rs.next();
            String eier = rs.getString("eier");
            String hjelper = rs.getString("hjelp");
            innlegg.setId(innleggID);
            innlegg.setKønummer(rs.getInt("kønummer"));
            Plassering plass = new Plassering(rs.getString("bygg"), rs.getInt("etasje"), rs.getString("rom"), rs.getInt("bord"), rs.getString("emnekode"));
            innlegg.setPlass(plass);
            innlegg.setTid(rs.getLong("tid"));
            innlegg.setKommentar(rs.getString("kommentar"));
            innlegg.setEmnekode(rs.getString("emnekode"));
            lukkForbindelse();
            innlegg.setEier(getBruker(eier));
            innlegg.setHjelp(getBruker(hjelper));
            innlegg.setBrukere(getBrukereIInnlegg(innleggID));
            innlegg.setOvinger(getØvingerTilBrukereIInnlegg(innleggID, innlegg.getBrukere()));
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getInnleggFraID()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getInnleggFraID - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectInnlegg);
        }
        lukkForbindelse();

        return innlegg;
    }
    /*    public synchronized boolean oppdaterØving(Øving øving) {
     boolean ok = false;
     System.out.println("oppdaterØving()");
     PreparedStatement psCountØving = null;
     PreparedStatement psDeleteØving = null;
     PreparedStatement psInsertØving = null;

     int antall;
     int sjekker;

     try {
     åpneForbindelse();
     psCountØving = forbindelse.prepareStatement(sqlCountØvinger);
     ResultSet rs = null;
     rs = psCountØving.executeQuery();
     antall = rs.getInt("telling");

     if (antall > øving.getØvingantall()) {
     int mid = antall - øving.getØvingantall(); // antall øvinger ønsket fjernet
     psDeleteØving = forbindelse.prepareStatement(sqlDeleteØvinger);
     psDeleteØving.setInt(1, antall);
     psDeleteØving.setInt(2, øving.getØvingantall());

     sjekker = psDeleteØving.executeUpdate();
     if (sjekker > 0) {
     ok= true;
     }
     } else if (antall < øving.getØvingantall()) {
     int mid = øving.getØvingantall() - antall; // antall øvinger ønsket lagt til. 10-5
     for (int r = mid + 1; r <= øving.getØvingantall(); r++) { // teller fra ønsket +1 slik at de nye som blir laget får riktig øvingsnr.
     psInsertØving = forbindelse.prepareStatement(sqlInsertØving);
     psInsertØving.setInt(1, r);
     psInsertØving.setString(2, øving.getEmnekode());
     sjekker = psInsertØving.executeUpdate();
     if (sjekker > 0) {
     ok= true;
     }
     }
     }

     } catch (SQLException e) {
     Opprydder.rullTilbake(forbindelse);
     Opprydder.skrivMelding(e, "oppdaterØving()");
     } catch (Exception e) {
     Opprydder.skrivMelding(e, "oppdaterØving - ikke sqlfeil");
     } finally {
     Opprydder.settAutoCommit(forbindelse);
     //Opprydder.lukkSetning(psUpdateBruker);
     }
     lukkForbindelse();
     return ok;
     }
     */

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

    public synchronized ArrayList<Bruker> getFaglærerBruker(String fornavn, String etternavn, int brukertype) {
        Bruker b = null;
        ResultSet res;
        System.out.println("getFaglærer()");
        PreparedStatement psSelectBruker = null;
        ArrayList<Bruker> faglærer = new ArrayList<Bruker>();

        try {
            åpneForbindelse();
            psSelectBruker = forbindelse.prepareStatement(sqlSelectpaabrukertype);
            psSelectBruker.setString(1, fornavn);
            psSelectBruker.setString(2, etternavn);
            psSelectBruker.setInt(3, brukertype);
            res = psSelectBruker.executeQuery();
            while (res.next()) {
                b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                faglærer.add(b);
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
        return faglærer;
    }

    // KRAVGRUPPE //
    public synchronized boolean registrerKravgruppe(Kravgruppe kg) {
        boolean ok = false;
        System.out.println("registrerKravgruppe()");
        PreparedStatement psInsertKravgruppe = null;

        try {
            åpneForbindelse();
            psInsertKravgruppe = forbindelse.prepareStatement(sqlInsertKravgruppe);
            psInsertKravgruppe.setInt(1, kg.getGruppeID());
            psInsertKravgruppe.setString(2, kg.getEmnekode());
            psInsertKravgruppe.setInt(3, kg.getAntallgodkj());

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
                Kravgruppe k = new Kravgruppe(res.getInt("gruppeID"), emnekode, res.getInt("antall"), res.getString("beskrivelse"));
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

    /**
     * denne metoden skal oppdatere en kravgruppe, altså øvinger som hører
     * sammen ** public boolean oppdaterKravgruppe(KravGruppe kravkruppe){
     * boolean ok = false; System.out.println("oppdaterKravgruppe()");
     * PreparedStatement psUpdateKravGruppe = null;
     *
     * try { åpneForbindelse(); psUpdateKravGruppe =
     * forbindelse.prepareStatement(sqlUpdateKravGruppe);
     * psUpdateKravGruppe.setString(1, kravgruppe.getGruppeID());
     * psUpdateKravGruppe.setString(2, kravgruppe.getEmnekode());
     * psUpdateKravGruppe.setInt(3, kravgruppe.getAntGodkjent());
     *
     * int i = psUpdateKravGruppe.executeUpdate(); if (i > 0) { ok = true; }
     *
     * } catch (SQLException e) { Opprydder.rullTilbake(forbindelse);
     * Opprydder.skrivMelding(e, "oppdaterKravGruppe()"); } catch (Exception e)
     * { Opprydder.skrivMelding(e, "oppdaterKravGruppe - ikke sqlfeil"); }
     * finally { Opprydder.settAutoCommit(forbindelse);
     *
     * }
     * lukkForbindelse(); return ok; }
     *
     */
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
            // Opprydder.lukkSetning(psInsertKravgruppe);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized Innlegg getInnleggFraHjelpEmne(String hjelp, String emnekode) {

        PreparedStatement psSelectInnleggFraHjelpEmne = null;
        ResultSet res;
        Innlegg innlegg = null;

        try {
            åpneForbindelse();
            psSelectInnleggFraHjelpEmne = forbindelse.prepareStatement(sqlSelectInnleggFraHjelpEmne);
            psSelectInnleggFraHjelpEmne.setString(1, hjelp);
            psSelectInnleggFraHjelpEmne.setString(2, emnekode);
            res = psSelectInnleggFraHjelpEmne.executeQuery();
            if (res.next()) {
                innlegg = new Innlegg();
                innlegg.setId(res.getInt("innleggsid"));
            }

        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getInnleggFraHjelpEmne()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getInnleggFraHjelpEmne - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectInnleggFraHjelpEmne);
        }
        lukkForbindelse();
        return innlegg;
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
            // Opprydder.lukkSetning(psInsertKravgruppe);
        }
        lukkForbindelse();
        return ok;
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
                Emne f = new Emne(res.getString("emnenavn"), res.getString("emnekode"), res.getString("beskrivelse"));
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

    public boolean fjernKoInnlegg(int koID) {
        System.out.println("fjernKoInnlegg()");
        PreparedStatement psfjernKoInnlegg = null;
        boolean ok = false;
        try {
            åpneForbindelse();
            psfjernKoInnlegg = forbindelse.prepareStatement(sqlDeleteKoInnleggFraID);

            psfjernKoInnlegg.setInt(1, koID);

            int i = psfjernKoInnlegg.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "fjernKoInnlegg()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "fjernKoInnlegg - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psfjernKoInnlegg);
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
                Bruker eier = new Bruker();
                eier.setBrukernavn(res.getString("eier"));
                innlegg.setEier(eier);
                //innlegg.setBrukere(null); //TODO
                innlegg.setHjelp(getBruker(res.getString("hjelp"))); //TODO
                innlegg.setKønummer(res.getInt("kønummer"));
                //innlegg.setOvinger(null); //TODO
                innlegg.setTid((System.currentTimeMillis() - res.getTimestamp("tid").getTime()) / 60000);  //TODO
                innlegg.setId(res.getInt("innleggsid"));
                Plassering plassering = new Plassering();
                plassering.setBygning(res.getString("bygg"));
                plassering.setEtasje(res.getInt("etasje"));
                plassering.setRom(res.getString("rom"));
                innlegg.setPlass(plassering);
                innlegg.setKommentar(res.getString("kommentar"));

                returnen.add(innlegg);
            }

            //MAY OR MAY NOT BE NEEDED
            for (int i = 0; i < returnen.size(); i++) {
                åpneForbindelse();
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
                lukkForbindelse();
                returnen.get(i).setOvinger(getØvingerTilBrukereIInnlegg(returnen.get(i).getId(), brukerne));
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
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectDistinctBygg);

            psUpdateFagKoAktiv.setString(1, emnekode);

            res = psUpdateFagKoAktiv.executeQuery();
            while (res.next()) {
                mid.add(res.getString("bygg"));
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
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public String[] getUnikeEtasjer(String emnekode, String bygg) {
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectDistinctEtasje);

            psUpdateFagKoAktiv.setString(1, emnekode);
            psUpdateFagKoAktiv.setString(2, bygg);

            res = psUpdateFagKoAktiv.executeQuery();
            while (res.next()) {
                mid.add(res.getString("etasje"));
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
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public String[] getUnikeRom(String emnekode, String bygg, String etasje) {
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectDistinctRom);
            int etasje2 = Integer.parseInt(etasje);
            psUpdateFagKoAktiv.setString(1, emnekode);
            psUpdateFagKoAktiv.setString(2, bygg);
            psUpdateFagKoAktiv.setInt(3, etasje2);

            res = psUpdateFagKoAktiv.executeQuery();
            while (res.next()) {
                mid.add(res.getString("rom"));
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
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public String[] getUnikeBord(String emnekode, String bygg, String etasje, String rom) {
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;
        ArrayList<String> mid = new ArrayList<String>();
        ResultSet res;

        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectDistinctBord);
            int etasje2 = Integer.parseInt(etasje);
            psUpdateFagKoAktiv.setString(1, emnekode);
            psUpdateFagKoAktiv.setString(2, bygg);
            psUpdateFagKoAktiv.setInt(3, etasje2);
            psUpdateFagKoAktiv.setString(4, rom);

            res = psUpdateFagKoAktiv.executeQuery();
            while (res.next()) {
                mid.add(res.getString("bord"));
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
        String[] returnen = new String[mid.size()];
        if (mid.size() > 0) {
            for (int i = 0; i < mid.size(); i++) {
                returnen[i] = mid.get(i);
            }
        }
        return returnen;
    }

    public synchronized String oppdaterØvingsBeskrivelse(String emnekode, String beskrivelse) {
        String ok = null;
        System.out.println("oppdaterBruker()");
        PreparedStatement psUpdateEmne = null;
        try {
            åpneForbindelse();
            psUpdateEmne = forbindelse.prepareStatement(sqlUpdageOvingsbeskrivelse);
            psUpdateEmne.setString(1, beskrivelse);
            psUpdateEmne.setString(2, emnekode);
            int i = psUpdateEmne.executeUpdate();
            if (i > 0) {
                ok = beskrivelse;
                System.out.println("inne i if(i=0) " + beskrivelse);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterØvingsBeskrivelse()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterØvingsbeskrivelse - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
//Opprydder.lukkSetning(psUpdateBruker); 
        }
        lukkForbindelse();
        return ok;
    }

    public ArrayList<Øving> getUgjorteØvinger(String emnekode, String brukernavn) {
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;
        ArrayList<Øving> returnen = new ArrayList<Øving>();
        ResultSet res;

        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectAlleUgjorteOvinger);

            psUpdateFagKoAktiv.setString(1, emnekode);
            psUpdateFagKoAktiv.setString(2, brukernavn);

            res = psUpdateFagKoAktiv.executeQuery();

            while (res.next()) {

                Øving øving = new Øving();
                øving.setEmnekode(emnekode);
                øving.setGruppeid(res.getInt("gruppeid"));
                øving.setObligatorisk(res.getBoolean("obligatorisk"));
                øving.setØvingsnr(res.getInt("øvingsnummer"));
                returnen.add(øving);
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

        return returnen;
    }

    public int getAntallOvingerIFag(String emnekode) {
        System.out.println("updateFagKoAktiv()");
        PreparedStatement psUpdateFagKoAktiv = null;
        int returnen = 0;
        ResultSet res;

        try {
            åpneForbindelse();
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectCountOvingerIFag);

            psUpdateFagKoAktiv.setString(1, emnekode);

            res = psUpdateFagKoAktiv.executeQuery();

            while (res.next()) {

                returnen = res.getInt("tall");
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

        return returnen;
    }
    
    public ArrayList<Boolean> getBrukerGodkjentArbeidskravIEmne(String brukernavn, String emnekode){
        PreparedStatement psSelectBGAIE = null;
        ArrayList<Boolean> lista = null;
        try{
            åpneForbindelse();
            psSelectBGAIE = forbindelse.prepareStatement(sqlSelectGodkjenteØvingerKravgruppeBruker);
            psSelectBGAIE.setString(1, brukernavn);
            psSelectBGAIE.setString(2, emnekode);
            ResultSet res = psSelectBGAIE.executeQuery();
            lista = new ArrayList();
            while(res.next()){
                int antØving = res.getInt("antallfullført");
                int antØvingIKrav = res.getInt("antall");
                if(antØving < antØvingIKrav){
                    lista.add(Boolean.FALSE);
                } else {
                    lista.add(Boolean.TRUE);
                }
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getFagKoAktiv()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getFagKoAktiv - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectBGAIE);
        }
        lukkForbindelse();
        
        return lista;
        
    }
    
    

    public boolean mekkeinnlegg(Plassering plass, ArrayList<Bruker> brukerne, String emnekode, String beskrivelse) {

        System.out.println("mekkeinnlegg()");
        PreparedStatement psUpdateFagKoAktiv = null;
        boolean returnen = true;
        ResultSet res;
        System.out.println("Jeg kom inn her");
        try {
            åpneForbindelse();

            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectSisteInnleggsid);
            res = psUpdateFagKoAktiv.executeQuery();
            int innleggsid = 0;
            while (res.next()) {
                innleggsid = res.getInt("innleggsid");
            }
            innleggsid++;

            int konummer = 0;
            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlSelectSisteKo);
            psUpdateFagKoAktiv.setString(1, emnekode);
            res = psUpdateFagKoAktiv.executeQuery();
            while (res.next()) {
                konummer = res.getInt("kønummer");
            }

            psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlInsertKøInnlegg);

            psUpdateFagKoAktiv.setInt(1, innleggsid);
            psUpdateFagKoAktiv.setInt(2, konummer);
            psUpdateFagKoAktiv.setString(3, brukerne.get(0).getBrukernavn());
            psUpdateFagKoAktiv.setString(4, plass.getBygning());
            psUpdateFagKoAktiv.setInt(5, plass.getEtasje());
            psUpdateFagKoAktiv.setString(6, plass.getRom());
            psUpdateFagKoAktiv.setInt(7, plass.getBord());
            psUpdateFagKoAktiv.setString(8, emnekode);
            psUpdateFagKoAktiv.setString(9, "");
            psUpdateFagKoAktiv.setString(10, beskrivelse);

            System.out.println("Og her");

            int mid = psUpdateFagKoAktiv.executeUpdate();

            boolean sofarok = false;
            if (mid > 0) {
                sofarok = true;
            }

            for (int i = 0; i < brukerne.size(); i++) {
                psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlInsertBrukereIInnlegg);
                psUpdateFagKoAktiv.setInt(1, innleggsid);
                psUpdateFagKoAktiv.setString(2, brukerne.get(i).getBrukernavn());
                psUpdateFagKoAktiv.executeUpdate();

                for (int a = 0; a < brukerne.get(i).getØvinger().size(); a++) {
                    psUpdateFagKoAktiv = forbindelse.prepareStatement(sqlInsertOvingerIInnlegg);
                    psUpdateFagKoAktiv.setInt(1, innleggsid);
                    psUpdateFagKoAktiv.setString(2, brukerne.get(i).getBrukernavn());
                    psUpdateFagKoAktiv.setInt(3, brukerne.get(i).getØvinger().get(a).getØvingsnr());
                    psUpdateFagKoAktiv.setString(4, emnekode);
                    psUpdateFagKoAktiv.executeUpdate();

                }

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

        return returnen;
    }

}
