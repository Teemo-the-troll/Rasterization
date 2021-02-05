package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ScreenManager {

    private final Set<Coordinate> selectedPoints = new HashSet<>();

    public void select(Coordinate coordinate) {
        selectedPoints.add(coordinate);
        System.out.println(coordinate.getX() + ", " + coordinate.getY());
    }

    public void unselect(Coordinate coordinate) {
        selectedPoints.remove(coordinate);
    }

    public Coordinate[] sortCoordinatesByYasc() {
        return selectedPoints.stream().sorted(Comparator.comparingInt(Coordinate::getY)).toArray(Coordinate[]::new);
    }

    public Coordinate[] getCoordinates() {
        return selectedPoints.toArray(Coordinate[]::new);
    }

    public boolean isSelected(Coordinate coordinate) {
        return selectedPoints.contains(coordinate);
    }

    public Set<Coordinate> getSelectedPoints() {
        return selectedPoints;
    }


    /*kod prevzat z Maths.cz: https://maths.cz/clanky/124-analyticka-geometrie-poloha-bodu-vuci-primce
     *
     * prelozen z programovaciho jazyku C
     *
     * */

    private boolean porovnej(int u1, int u2, int u3, int a1, int a2, int b1, int b2) {
        int x = (u1 * a1 + u2 * a2 + u3);
        int y = (u1 * b1 + u2 * b2 + u3);
        return (x * y > 0);
    }

    //kontroluje jestli je bod D v uhlu ABC s vrcholem v B
    private boolean zkontroluj(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {

        int[] u = {-b.getY() + a.getY(), b.getX() - a.getX(), 0};//primka AB - dosadit A, kontrola s C
        int[] v = {-c.getY() + b.getY(), c.getX() - b.getX(), 0};//primka BC - dosadit B, kontrola s A

        u[2] = -u[0] * a.getX() - u[1] * a.getY();
        v[2] = -v[0] * b.getX() - v[1] * b.getY();

        boolean x = porovnej(u[0], u[1], u[2], c.getX(), c.getY(), d.getX(), d.getY());
        boolean y = porovnej(v[0], v[1], v[2], a.getX(), a.getY(), d.getX(), d.getY());

        return x && y;
    }


    public boolean isFilledIn(Coordinate coordinate) { // TODO: Implement this
        if (selectedPoints.size() < 3)
            return false;

        return zkontroluj(this.getCoordinates()[1], this.getCoordinates()[0], this.getCoordinates()[2], coordinate) &&
                zkontroluj(this.getCoordinates()[0], this.getCoordinates()[1], this.getCoordinates()[2], coordinate) &&
                zkontroluj(this.getCoordinates()[1], this.getCoordinates()[2], this.getCoordinates()[0], coordinate);
    }
}
