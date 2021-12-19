package ru.anrad.learnup.asserts;

import lombok.NoArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import ru.anrad.learnup.enams.CategoryType;

@NoArgsConstructor
public class IsCategoryExists extends TypeSafeMatcher<String> {
    public static IsCategoryExists isCategoryExists() {
        return new IsCategoryExists();
    }

    @Override
    protected boolean matchesSafely(String actualCategoryTitle) {
        try {
            CategoryType.valueOf(actualCategoryTitle.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("No such category in our dictionary");
    }
}
