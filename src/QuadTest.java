import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class QuadTest {

    Quad root = new Quad(800);

    public QuadTest(int n) {

        for (int i = 0; i < n; i++) {

            int signX = 1;
            if (Math.random() < .5) signX = -1;

            int signY = 1;
            if (Math.random() < .5) signY = -1;

            float x = (float) Math.random() * 400 * signX;
            float y = (float) Math.random() * 400 * signY;
            float mass = (float) Math.random() * 10 + 4;

            Body b = new Body(x, y, 0, 0, mass);
            //System.out.println(b);
            root.insert(b);
        }

        System.out.println("Count: " + root.count());
        System.out.println("Total mass: " + root.getTotalMass());
        final Point2D.Float centerOfMass = root.getCenterOfMass();
        System.out.println("Root center of mass: " + centerOfMass.getX() + ", " + centerOfMass.getY());

        JComponent view = new JComponent() {
            @Override
            public void paint(Graphics g) {

                g.translate(500, 500);
                visualize(root, (Graphics2D) g);

                g.setColor(Color.blue);
                ((Graphics2D) g).fill(new Rectangle2D.Float((float) centerOfMass.getX(), (float) centerOfMass.getY(), 8, 8));
            }
        };
        view.setPreferredSize(new Dimension(1000, 1000));

        JFrame frame = new JFrame("QuadTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void visualize(Quad q, Graphics2D g) {

        int l = (int) q.length / 2;
        g.drawLine(Math.round(q.x - l), Math.round(q.y - l), Math.round(q.x - l), Math.round(q.y + l));
        g.drawLine(Math.round(q.x - l), Math.round(q.y - l), Math.round(q.x + l), Math.round(q.y - l));
        g.drawLine(Math.round(q.x + l), Math.round(q.y - l), Math.round(q.x + l), Math.round(q.y + l));
        g.drawLine(Math.round(q.x - l), Math.round(q.y + l), Math.round(q.x + l), Math.round(q.y + l));

        if (q.isLeaf()) {
            g.fill(new Ellipse2D.Float(q.body.px, q.body.py, q.body.mass, q.body.mass));
        }
        else {
            if (q.NW != null) visualize(q.NW, g);
            if (q.NE != null) visualize(q.NE, g);
            if (q.SW != null) visualize(q.SW, g);
            if (q.SE != null) visualize(q.SE, g);
        }
    }

    public static void main(String[] args) {

        new QuadTest(30);
    }
}
