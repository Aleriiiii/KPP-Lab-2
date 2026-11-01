package ua.khnu.kpp;

public final class VariantPolicy implements CompletionPolicy {
    @Override
    public boolean allows(int units, int defects, boolean approved) {
        return units >= 8 && (defects == 0 || approved);
    }
}