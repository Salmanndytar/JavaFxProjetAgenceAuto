package sourse.classeImple;

import sourse.classes.User;
import sourse.interphaces.IUser;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserImple  implements IUser {
    public DB db = new DB();
    @Override
    public User getConnection(String email, String password) {
        User usr = null;

        try {

            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            db.preparedStatement(sql);
            db.getPstmt().setString(1, email);
            db.getPstmt().setString(2, password);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                usr = new User();
                usr.setId(rs.getInt("id_user"));
                usr.setEmail(rs.getString("email"));
                usr.setPassword(rs.getString("password"));
                Object agent= rs.getObject("agent");
                Object caissier= rs.getObject("caissier");
                Object admin= rs.getObject("admin");
                usr.setAgent(null);
                usr.setCaissier(null);
                usr.setAdmin(null);
                if (agent !=null){
                    usr.setAgent(new AgentImpl().getAgent((Integer)agent));}
                if (caissier !=null){
                    usr.setCaissier(new CaissierImpl().getCaissier((Integer)caissier));}
                if (admin !=null){
                    usr.setAdmin(new AdminImpl().getAdmin((Integer)admin));}
            }
        }
        catch (Exception ex){ex.printStackTrace();}

        return usr;
    }
    @Override
    public User getUser(int id) {
        User user = null;
        try {
            String sql = "SELECT * FROM user WHERE id= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setAgent(new AgentImpl().getAgent(rs.getInt(4)));
                user.setCaissier(new CaissierImpl().getCaissier(rs.getInt(5)));
                user.setAdmin(new AdminImpl().getAdmin(rs.getInt(6)));
                user.setDate(LocalDate.parse(rs.getString(7)));
            }
        }
        catch (Exception ex){ex.printStackTrace();}

        return user;
    }
    @Override
    public int suppUser(int id) {
        int ok=0;
        String sql = "DELETE FROM user WHERE id_user= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }
    @Override
    public int adduserAg(User user) {
        int insert=0;
        try
        {
            String sql="INSERT INTO user VALUES(NULL, ?, ?, ?, NULL, NULL, ?)";

            db.preparedStatement(sql);
            db.getPstmt().setString(1,user.getEmail());
            db.getPstmt().setString(2,user.getPassword());
            db.getPstmt().setInt(3,user.getAgent().getId());
            db.getPstmt().setString(4, user.getDate().toString());

            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int adduserCa(User user) {
        String sql="INSERT INTO user VALUES(NULL, ?, ?, NULL, ?, NULL, ?)";
        int insert=0;
        try
        {
            db.preparedStatement(sql);
            db.getPstmt().setString(1,user.getEmail());
            db.getPstmt().setString(2,user.getPassword());
            db.getPstmt().setInt(3,user.getCaissier().getId());
            db.getPstmt().setString(4, user.getDate().toString());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int adduserAd(User user) {
        int insert=0;
        try
        {
            String sql="INSERT INTO user VALUES(NULL, ?, ?, NULL, NULL, ?, ?)";

            db.preparedStatement(sql);
            db.getPstmt().setString(1,user.getEmail());
            db.getPstmt().setString(2,user.getPassword());
            db.getPstmt().setInt(3,user.getAdmin().getId());
            db.getPstmt().setString(4, user.getDate().toString());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatUser(User user) {
        int edite=0;
        try
        {
            String sql="UPDATE user SET email = ?,password = ? WHERE id_user  = ?";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,user.getEmail());
            db.getPstmt().setString(2,user.getPassword());
            db.getPstmt().setInt(3,user.getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<User> listeUserAg() {
        String sql="SELECT u.id_user,u.email,u.password,u.agent,u.date_enreg FROM user u,agent a WHERE u.agent=a.id_agent";
        List<User>userList= new ArrayList<User>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next()) {
                User usr = new User();
                usr.setId(rs.getInt("u.id_user"));
                usr.setEmail(rs.getString("u.email"));
                usr.setPassword(rs.getString("u.password"));
                usr.setAgent(new AgentImpl().getAgent(rs.getInt("u.agent")));
                usr.setDate(LocalDate.parse(rs.getString("u.date_enreg")));
                userList.add(usr);
            }
        }catch (Exception ex){ex.printStackTrace();}
        return userList;
    }
    @Override
    public List<User> listeUserC() {
        String sql="SELECT u.id_user,u.email,u.password,u.caissier,u.date_enreg FROM user u,caissier c WHERE u.caissier=c.id_caissier";
        List<User>userList= new ArrayList<User>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next()) {
                User usr = new User();;
                usr.setId(rs.getInt("u.id_user"));
                usr.setEmail(rs.getString("u.email"));
                usr.setPassword(rs.getString("u.password"));
                usr.setCaissier(new CaissierImpl().getCaissier(rs.getInt("u.caissier")));
                usr.setDate(LocalDate.parse(rs.getString("u.date_enreg")));
                userList.add(usr);
            }
        }catch (Exception ex){ex.printStackTrace();}
        return userList;
    }
    @Override
    public List<User> listeUserA() {
        String sql="SELECT u.id_user,u.email,u.password,u.admin,u.date_enreg FROM user u,admin a WHERE u.admin=a.id";
        List<User>userList= new ArrayList<User>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next()) {
                User usr = new User();
                usr.setId(rs.getInt("u.id_user"));
                usr.setEmail(rs.getString("u.email"));
                usr.setPassword(rs.getString("u.password"));
                usr.setAdmin(new AdminImpl().getAdmin(rs.getInt("u.admin")));
                usr.setDate(LocalDate.parse(rs.getString("u.date_enreg")));
                userList.add(usr);
            }
            }catch (Exception ex){ex.printStackTrace();}
            return userList;

    }
    @Override
    public List<User> listeUserComplet() {
        String sql="SELECT * FROM user ";
        List<User>userList= new ArrayList<User>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next()) {
                User usr = new User();
                usr.setId(rs.getInt(1));
                usr.setEmail(rs.getString(2));
                usr.setPassword(rs.getString(3));
                usr.setAgent(new AgentImpl().getAgent(rs.getInt(4)));
                usr.setCaissier(new CaissierImpl().getCaissier(rs.getInt(5)));
                usr.setAdmin(new AdminImpl().getAdmin(rs.getInt(6)));
                usr.setDate(LocalDate.parse(rs.getString(7)));

                userList.add(usr);
            }
        }catch (Exception ex){ex.printStackTrace();}
        return userList;
    }
}
