import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegmentList lineSegmentList;

    private class LineSegmentList {
        private LineSegment[] lineSegments;
        private int nSize;

        public LineSegmentList() {
            lineSegments = new LineSegment[1];
            nSize = 0;
        }

        private void resize(int capacity) {
            LineSegment[] newLineSegments = new LineSegment[capacity];

            for (int i = 0; i < nSize; i++) {
                newLineSegments[i] = lineSegments[i];
            }

            lineSegments = newLineSegments;
        }

        public void addLineSegment(LineSegment lineSegment) {
            if (nSize == lineSegments.length) {
                resize(nSize * 2);
            }

            lineSegments[nSize++] = lineSegment;
        }

        public LineSegment[] getLineSegmentList() {
            LineSegment[] segments = new LineSegment[this.lineSegments.length];

            for (int i = 0; i < segments.length; i++) {
                segments[i] = this.lineSegments[i];
            }

            return segments;
        }

        public int size() {
            return nSize;
        }

        public void adjustSize() {
            LineSegment[] adjusted = new LineSegment[nSize];

            for (int i = 0; i < nSize; i++) {
                adjusted[i] = lineSegments[i];
            }

            lineSegments = adjusted;
        }
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            this.points[i] = points[i];
        }

        Arrays.sort(this.points);
        checkDuplicates();
        createLineSegmentList();
    }

    private void checkDuplicates() {
        Point prev = null;

        for (int i = 0; i < points.length; i++) {
            if (prev != null && prev.compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }

            prev = points[i];
        }
    }

    private void createLineSegmentList() {
        this.lineSegmentList = new LineSegmentList();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int p = k + 1; p < points.length; p++) {
                        if (points[i] == null || points[j] == null || points[k] == null || points[p] == null) {
                            throw new IllegalArgumentException();
                        }

                        if (points[i] == points[j] || points[i] == points[k] || points[i] == points[p] ||
                            points[j] == points[k] || points[j] == points[p] || points[k] == points[p]) {
                            throw new IllegalArgumentException();
                        }

                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[p]);

                        if (slope1 == slope2 && slope2 == slope3) {
                            lineSegmentList.addLineSegment(new LineSegment(points[i], points[p]));
                        }
                    }
                }
            }
        }

        lineSegmentList.adjustSize();
    }

    public int numberOfSegments() {
        return lineSegmentList.size();
    }

    public LineSegment[] segments() {
        return lineSegmentList.getLineSegmentList();
    }
}
