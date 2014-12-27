/**
 *
 */
package com.ibatis.scorecardmodel.bo.user;


import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * Karel Tejnora <karel.tejnora@zoomint.com>; Zoom International, s. r. o.
 * Created At: 6:05:42 PM
 */
public class PasswordBean implements Serializable {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 7451010184530081545L;

    /**
     * Id - Primary Key
     */
    private Integer id;
    /**
     * UserId
     */
    private Integer userId;
    /**
     * Encrypted password has in hexa decimal String
     */
    private String password;
    /**
     * Password salt
     */
    private String salt;
    /**
     * When the password was created
     */
    private Date created;

    /**
     * Creates instance with nulls
     */
    public PasswordBean() {
        this(null,null,null,null,null);
    }

    /**
     * Creates an instance with all required parameters,
     * id and created date are generated by DB
     * @param userId
     * @param password
     * @param salt
     */
    public PasswordBean(Integer userId, String password, String salt) {
        this( null, userId, password, salt, new Date());
    }

    /**
     * Creates instance with all parameters
     * @param id
     * @param userId
     * @param password
     * @param salt
     * @param created
     */
    public PasswordBean(Integer id, Integer userId, String password, String salt, Date created) {
        super();
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.salt = salt;
        this.created = created;
    }
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }
    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }
    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return int - hashCode from all properties
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((salt == null) ? 0 : salt.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    /**
     * @return true if obj is equal to this object
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        PasswordBean other = ( PasswordBean ) obj;
        if( created == null ) {
            if( other.created != null ) {
                return false;
            }
        } else if( !created.equals( other.created ) ) {
            return false;
        }
        if( id == null ) {
            if( other.id != null ) {
                return false;
            }
        } else if( !id.equals( other.id ) ) {
            return false;
        }
        if( password == null ) {
            if( other.password != null ) {
                return false;
            }
        } else if( !password.equals( other.password ) ) {
            return false;
        }
        if( salt == null ) {
            if( other.salt != null ) {
                return false;
            }
        } else if( !salt.equals( other.salt ) ) {
            return false;
        }
        if( userId == null ) {
            if( other.userId != null ) {
                return false;
            }
        } else if( !userId.equals( other.userId ) ) {
            return false;
        }
        return true;
    }

    /**
     * @return String - representation of this object without password and salt
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "PasswordBean [id=" + id + ", userId=" + userId + ", created=" + created + "]";
    }


    /**
     * Compares this password to otherPassword after otherPassword is encoded
     * @param otherPassword
     * @return true - encoded passwords are same
     */
    public boolean samePassword( final char[] otherPassword ) {
//        PasswordEncoder encoder = null;
//        if( salt == null ) {
////            encoder = PasswordEncoder.newInstanceLegacyScorecard();
//        } else {
////            encoder = PasswordEncoder.newInstanceRandom49();
//        }
//        try {
////            String otherEncoded = encoder.encodeAsHexString( otherPassword, salt);
////            if( otherEncoded == null ) {
////                return false;
////            }
////            return otherEncoded.equals( getPassword() );
//        } catch( ParseException e ) {
            return false;
//        }
    }
}