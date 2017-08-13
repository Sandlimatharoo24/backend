package org.backend.ims.model;

public class Incidents {

	 
    private long id;
    private String type;
    private String comment;

    public Incidents(){
        id=0;
    }
     
    public Incidents(long id, String type, String comment){
        this.id = id;
        this.type=type;
        this.comment = comment;
    }
     
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Incidents other = (Incidents) obj;
        if (id != other.id)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "User [id=" + id + ", type=" + type + ", comment=" + comment
              +"]";
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
 
}
