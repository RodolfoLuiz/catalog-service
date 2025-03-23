package com.catalog.domain.category;

import com.catalog.domain.exceptions.DomainException;
import com.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class CategoryTest {
    @Test
    public void givenAValidaParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = " ";
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "fi ";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = """
                Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a necessidade de renovação processual garante a contribuição de um grupo importante na determinação do retorno esperado a longo prazo.
                É claro que o aumento do diálogo entre os diferentes setores produtivos
                """;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Filmes";
        final var expectedDescription = "";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
    }
    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Filmes";
        final var expectedDescription = "Category";
        final var expectedIsActive = false;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated(){
        final String expectedName = "Filmes";
        final var expectedDescription = "Category";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        final Instant updatedAt = actualCategory.getUpdatedAt();
        Assertions.assertNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.isActive());

        final var actualCategory2 = actualCategory.deactivate();
        Assertions.assertDoesNotThrow(() -> actualCategory2.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory2.getDeletedAt());
        Assertions.assertFalse(actualCategory2.isActive());
        Assertions.assertTrue(actualCategory2.getUpdatedAt().isAfter(updatedAt));
    }
    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryInactivated(){
        final String expectedName = "Filmes";
        final var expectedDescription = "Category";
        final var expectedIsActive = false;
        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        final Instant updatedAt = actualCategory.getUpdatedAt();
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertFalse(actualCategory.isActive());

        final var actualCategory2 = actualCategory.activate();
        Assertions.assertDoesNotThrow(() -> actualCategory2.validate(new ThrowsValidationHandler()));
        Assertions.assertNull(actualCategory2.getDeletedAt());
        Assertions.assertTrue(actualCategory2.isActive());
        Assertions.assertTrue(actualCategory2.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        final String expectedName = "Filmes";
        final var expectedDescription = "Category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory("Film","Cat3gory",expectedIsActive);
        final var create = actualCategory.getCreatedAt();
        final var update = actualCategory.getUpdatedAt();
        final var aCategory = actualCategory.update(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertEquals(actualCategory.getId(),aCategory.getId());
        Assertions.assertEquals(expectedName, aCategory.getName());
        Assertions.assertEquals(expectedDescription, aCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, aCategory.isActive());
        Assertions.assertTrue(aCategory.getUpdatedAt().isAfter(update));
        Assertions.assertEquals(aCategory.getCreatedAt(), create);
        Assertions.assertNull(aCategory.getDeletedAt());
    }

    @Test
    public void givenAInvalidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        final String expectedName = "Filmes";
        final var expectedDescription = "Category";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory("Film","Cat3gory",true);
        final var create = actualCategory.getCreatedAt();
        final var update = actualCategory.getUpdatedAt();
        Assertions.assertNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.isActive());
        final var aCategory = actualCategory.update(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertEquals(actualCategory.getId(),aCategory.getId());
        Assertions.assertEquals(expectedName, aCategory.getName());
        Assertions.assertEquals(expectedDescription, aCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, aCategory.isActive());
        Assertions.assertTrue(aCategory.getUpdatedAt().isAfter(update));
        Assertions.assertEquals(aCategory.getCreatedAt(), create);
        Assertions.assertNotNull(aCategory.getDeletedAt());
    }
    @Test
    public void givenAInvalidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated(){
        final String expectedName = null;
        final var expectedDescription = "Category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory("Film","Cat3gory",expectedIsActive);
        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        final var create = actualCategory.getCreatedAt();
        final var update = actualCategory.getUpdatedAt();
        Assertions.assertNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.isActive());
        final var aCategory = actualCategory.update(expectedName,expectedDescription,expectedIsActive);
        Assertions.assertEquals(actualCategory.getId(),aCategory.getId());
        Assertions.assertEquals(expectedName, aCategory.getName());
        Assertions.assertEquals(expectedDescription, aCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, aCategory.isActive());
        Assertions.assertTrue(aCategory.getUpdatedAt().isAfter(update));
        Assertions.assertEquals(aCategory.getCreatedAt(), create);
        Assertions.assertNull(aCategory.getDeletedAt());
    }
}
