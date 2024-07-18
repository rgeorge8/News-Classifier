package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks
        this.doubElements = _elements;
    }
    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
        if (_index < doubElements.length) {
            return doubElements[_index];
        }
        return (-1.00);
    }
    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks
        int elementLength = doubElements.length;
        if (_index > (elementLength - 1)) {
            doubElements[elementLength - 1] = _value;
        } else {
            doubElements[_index] = _value;
        }
    }
    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks
        return this.doubElements;
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return this.doubElements.length;
    }
    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks
        if ((_size == getVectorSize()) || (_size<=0)){
            return new Vector(this.doubElements);
        }
        if (_size< getVectorSize()){
            double resizedLess [] = new double[_size];
            for (int i = 0; i <_size ; i++) {
                resizedLess[i] = doubElements[i];
            }
            return new Vector(resizedLess);
        }
        double resizedMore [] = new double[_size];
        for (int i = 0; i <getVectorSize() ; i++) {
            resizedMore[i] = doubElements[i];
        }
        for (int i = getVectorSize(); i <_size; i++) {
            resizedMore[i] = -1;
        }
        return new Vector(resizedMore);
    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks
        int vLength = _v.getVectorSize();
        double[] resize;
        if (vLength > getVectorSize()) {
            doubElements = this.reSize(vLength).getAllElements();
            resize = _v.getAllElements();
        } else {
            resize = _v.reSize(getVectorSize()).getAllElements();
        }
        for (int i = 0; i < getVectorSize(); i++) {
            this.doubElements[i] += resize[i];
        }
        return new Vector(this.doubElements);
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks
        int vLength = _v.getVectorSize();
        double[] resize;
        if (vLength > getVectorSize()) {
            doubElements = this.reSize(vLength).getAllElements();
            resize = _v.getAllElements();
        } else {
            resize = _v.reSize(getVectorSize()).getAllElements();
        }
        for (int i = 0; i < getVectorSize(); i++) {
            this.doubElements[i] -= resize[i];
        }
        return new Vector(this.doubElements);
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks
        int vLength = _v.getVectorSize();
        double [] resize;
        double sum = 0.0;
        if (vLength > getVectorSize()){
            doubElements = this.reSize(vLength).getAllElements();
            resize = _v.getAllElements();
        }
        else{
            resize = _v.reSize(getVectorSize()).getAllElements();
        }
        for (int i = 0; i < getVectorSize(); i++) {
            sum += (this.doubElements[i] * resize[i]);
        }
        return sum;
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks
        int vLength = _v.getVectorSize();
        double [] resize;
        double sum = 0.0;
        double usquared = 0.0;
        double vsquared = 0.0;
        if (vLength > getVectorSize()){
            doubElements = this.reSize(vLength).getAllElements();
            resize = _v.getAllElements();
        }
        else{
            resize = _v.reSize(getVectorSize()).getAllElements();
        }
        for (int i = 0; i < getVectorSize(); i++) {
            sum += (this.doubElements[i] * resize[i]);
            usquared += (doubElements[i]*doubElements[i]);
            vsquared += (_v.getElementatIndex(i)*_v.getElementatIndex(i));
        }
        double rootu2 = Math.sqrt(usquared);
        double rootv2 = Math.sqrt(vsquared);
        double cosineSimilarity = (sum/((rootu2*rootv2)));
        return cosineSimilarity;
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}