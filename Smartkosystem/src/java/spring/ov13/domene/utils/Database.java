package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Vare;

public class Database {

    private String dbNavn;
    private Connection forbindelse;
 //  
 //  private final String sqlDeleteVare = "Delete from ov13.varer where varenr = ?";
    private final String sqlSelectAlleBrukere = "Select * from ov13.bruker order by etternavn";
   // private final String sqlInsertBruker = "insert into ov13.bruker values(?,?,?)";
    //private final String sqlUpdateVare = "update ov13.varer set varenavn=?,pris=? where varenr=?";
 //
    //TODO Endre databasenamn
    private final String sqlInsertBruker = "insert into ov13.varer values(?,?,?,?,?)"; //(fornavn, etternavn, brukernavn(epost), passord, brukertype 
    
    public Database(String dbNavn) {
        this.dbNavn = dbNavn;
    }

    
    public Database() {
    }

    private void åpneForbindelse() throws Exception {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            forbindelse = DriverManager.getConnection(dbNavn);
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
    
    public synchronized boolean registrerBruker(Bruker bruker){
        boolean ok = false;
        System.out.println("registrerBruker()");
        PreparedStatement psInsertBruker = null;

        try {
            åpneForbindelse();
            psInsertBruker = forbindelse.prepareStatement(sqlInsertBruker);
            psInsertBruker.setString(1, bruker.getBrukerNavn());
            psInsertBruker.setString(2, bruker.getFornavn());
            psInsertBruker.setString(3, bruker.getEtternavn());
            psInsertBruker.setInt(4, bruker.getBrukertype());
            

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
            Opprydder.lukkSetning(psInsertBruker);
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
                Bruker b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("brukertype"), res.getString("passord"));
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
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return brukerListe;
    }
}
    /*
    public synchronized boolean registrerVare(Vare v) {
        boolean ok = false;
        System.out.println("registrerVare()");
        PreparedStatement psInsertVare = null;

        try {
            åpneForbindelse();
            psInsertVare = forbindelse.prepareStatement(sqlInsertVare);
            psInsertVare.setInt(1, v.getVarenr());
            psInsertVare.setString(2, v.getVarenavn());
            psInsertVare.setInt(3, v.getPris());

            int i = psInsertVare.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerVare()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerVare - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psInsertVare);
        }
        lukkForbindelse();
        return ok;
    }


    public synchronized boolean oppdaterVare(Vare v) {
        boolean ok = false;
        System.out.println("oppdaterVare()");
        PreparedStatement psUpdateVare = null;

        try {
            åpneForbindelse();
            psUpdateVare = forbindelse.prepareStatement(sqlUpdateVare);
            psUpdateVare.setInt(3, v.getVarenr());
            psUpdateVare.setString(1, v.getVarenavn());
            psUpdateVare.setInt(2, v.getPris());
            System.out.println("pris: " + v.getPris());
            int i = psUpdateVare.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterPerson()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterPerson - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateVare);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean slettVare(Vare v) {
        boolean ok = false;
        System.out.println("slettPerson()");
        PreparedStatement psDeleteVare = null;

        try {
            åpneForbindelse();
            psDeleteVare = forbindelse.prepareStatement(sqlDeleteVare);
            psDeleteVare.setInt(1, v.getVarenr());

            int i = psDeleteVare.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "slettVare()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "slettVare - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psDeleteVare);
        }
        lukkForbindelse();
        return ok;

    }

}
