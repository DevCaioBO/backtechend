package com.fin.tech.DTOS;

public class GoogleUserInfo {
	 private String email;
	    private String name;
	    private String picture;
	    private String googleId;
	    
	    

	    public String getGoogleId() {
			return googleId;
		}
		public void setGoogleId(String googleId) {
			this.googleId = googleId;
		}
		public GoogleUserInfo(String email, String name, String picture) {
	        this.email = email;
	        this.name = name;
	        this.picture = picture;
	    }
	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getPicture() {
	        return picture;
	    }

	    public void setPicture(String picture) {
	        this.picture = picture;
	    }
}
