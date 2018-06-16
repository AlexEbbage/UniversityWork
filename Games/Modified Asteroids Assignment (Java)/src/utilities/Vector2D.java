package utilities;

import java.util.ArrayList;

public final class Vector2D {
	
	// mutable 2D vectors
    public double x, y;
    
    
    // constructor for zero vector
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }
       
    
    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    
    // constructor that copies the argument vector 
    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }
    
    
    // set coordinates
    public Vector2D set(double x, double y){
    	this.x = x;
    	this.y = y;
    	return this;
    }

    
    // set coordinates based on argument vector 
    public Vector2D set(Vector2D v){
    	this.x = v.x;
    	this.y = v.y;
    	return this;
    }
    

    // compare for equality (note Object type argument) 
    public boolean equals(Object o){
        return (this.equals(o));
    } 

    
    // String for displaying vector as text 
    public String toString(){
    	String output = "( "+this.x+", "+this.y+" )";
    	return output;
    }
    	
    
    //  magnitude (= "length") of this vector 
    public double mag(){
    	double length = Math.sqrt(x*x + y*y); 
    	return length;
    }

    
    // angle between vector and horizontal axis in radians in range [-PI,PI] 
    // can be calculated using Math.atan2 
    public double angle(){
    	double angle = Math.atan2(this.y, this.x);
        return angle;
    }

    
    // angle between this vector and another vector in range [-PI,PI] 
    public double angle(Vector2D other){
        double angle = other.angle() - this.angle();
        if (angle < - Math.PI)
            angle += 2 * Math.PI;
        else if (angle > Math.PI)
            angle -= 2* Math.PI;
        return angle;
    }

    
    // add argument vector 
    public Vector2D add(Vector2D v){
    	this.x += v.x;
    	this.y += v.y;
    	return this;
    }

    
    // add values to coordinates 
    public Vector2D add(double x, double y){
    	this.x += x;
    	this.y += y;
    	return this;
    }

    
    // weighted add - surprisingly useful
    public Vector2D addScaled(Vector2D v, double fac){
    	this.x += v.x * fac;
    	this.y += v.y * fac;
    	return this;
    }

    
    // subtract argument vector 
    public Vector2D subtract(Vector2D v){
    	this.x -= v.x;
    	this.y -= v.y;
    	return this;
    }

    
    // subtract values from coordinates 
    public Vector2D subtract(double x, double y){
    	this.x -= x;
    	this.y -= y;
    	return this;
    }


    // weighted subtract - surprisingly useful
    public Vector2D subtractScaled(Vector2D v, double fac){
        this.x -= v.x * fac;
        this.y -= v.y * fac;
        return this;
    }

    
    // multiply with factor 
    public Vector2D mult(double fac){
    	this.x *= fac;
    	this.y *= fac;
    	return this;
    }

    
    // rotate by angle given in radians 
    public Vector2D rotate(double angle){
    	   double rx = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
    	   double ry = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));
    	   this.x = rx;
    	   this.y = ry;
    	   return this;
    }

    
    // "dot product" ("scalar product") with argument vector 
    public double dot(Vector2D v){
    	return this.x * v.x + this.y + v.y;
    }

    
    // distance to argument vector 
    public double dist(Vector2D v){
    	double v0 = v.x - this.x;
    	double v1 = v.y - this.y;
        return Math.sqrt(v0*v0 + v1*v1);
    }

    
    // normalise vector so that magnitude becomes 1 
    public Vector2D normalise(){
        double length = mag(); 
     
        if (length != 0.0) {
        	double s = 1 / length;
            this.x = x*s;
            this.y = y*s;
        }
    	return this;
    }

    
    // wrap-around operation, assumes w> 0 and h>0
    public Vector2D wrap(double w, double h){
    	if(this.x > w){
    		this.x -= w;
    	}
    	if(this.x < 0){
    		this.x += w;
    	}
    	if(this.y > h){
    		this.y -= h;
    	}
    	if(this.y < 0){
    		this.y += h;
    	}
    	return this;
    }

    
    // construct vector with given polar coordinates  
    public static Vector2D polar(double angle, double mag){
    	return new Vector2D(mag * Math.cos(angle), mag * Math.sin(angle));
    }
    

	public static Vector2D random(double mag) {
		double angle = Math.random() * Math.PI * 2;
		return new Vector2D(mag * Math.cos(angle), mag * Math.sin(angle));
	}
}
