package it.pasqualini.util;

public class  Vector2  {
    Double x;
    Double y;

    public Vector2(Double x, Double y) {
        this.x = x;
        this.y = y;
    }


    public static Vector2 fromDirectionAndMagnitude(Double angleRad, Double magnitude) {
        return new Vector2(magnitude * Math.cos(angleRad),magnitude * Math.sin(angleRad));
    }

    public Double getAngle() {
        return Math.atan(y/x);
    }



    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public double distance(Vector2 vector2){
        return Math.sqrt(Math.pow( getX() -  vector2.getX(), 2) + Math.pow(getY() - vector2.getY(), 2));
    }



}
