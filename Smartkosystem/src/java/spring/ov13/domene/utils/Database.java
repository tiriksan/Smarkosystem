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
    private final String sqlUpdateBruker = "update ov13.bruker set fornavn=?,etternavn=?, brukertype=?, passord=? where brukernavn=?";
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

    public synchronized boolean oppdaterBruker(Bruker b){
        boolean ok = false;
        System.out.println("oppdaterBruker()");
        PreparedStatement psUpdateBruker = null;
        
        try{
            åpneForbindelse();
            psUpdateBruker = forbindelse.prepareStatement(sqlUpdateBruker);
            psUpdateBruker.setString(1, b.getBrukerNavn());
            psUpdateBruker.setString(2,b.getFornavn());
            psUpdateBruker.setString(3, b.getEtternavn());
            psUpdateBruker.setInt(4, b.getBrukertype());
            psUpdateBruker.setString(5, b.getPassord());
            int i = psUpdateBruker.executeUpdate();
            if(i>0){
                ok = true;
            }
            
            } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateBruker);
        }
        lukkForbindelse();
        return ok;
    }
}
            
            
            
        
    

    


