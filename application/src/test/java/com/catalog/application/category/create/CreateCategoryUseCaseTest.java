package com.catalog.application.category.create;

import com.catalog.domain.category.Category;
import com.catalog.domain.category.CategoryGateway;
import com.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                    return aCategory.getName().equals(expectedName)
                            && aCategory.getDescription().equals(expectedDescription)
                            && aCategory.isActive() == expectedIsActive
                            && aCategory.getId() != null
                            && aCategory.getCreatedAt() !=null
                            && aCategory.getUpdatedAt() != null
                            && aCategory.getDeletedAt() == null;
                }
                ));

    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_shouldReturnDomainException(){
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualExpection = useCase.execute(aCommand).getLeft();
        Assertions.assertEquals(expectedErrorMessage, actualExpection.firstError().message());
        Assertions.assertEquals(expectedErrorCount, actualExpection.getErrors().size());
        Mockito.verify(categoryGateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                            return aCategory.getName().equals(expectedName)
                                    && aCategory.getDescription().equals(expectedDescription)
                                    && aCategory.isActive() == expectedIsActive
                                    && aCategory.getId() != null
                                    && aCategory.getCreatedAt() !=null
                                    && aCategory.getUpdatedAt() != null
                                    && aCategory.getDeletedAt() != null;
                        }
                ));

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnGatewayException(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway exception";
        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualExpection = useCase.execute(aCommand).getLeft();
        Assertions.assertEquals(expectedErrorMessage, actualExpection.firstError().message());
        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                            return aCategory.getName().equals(expectedName)
                                    && aCategory.getDescription().equals(expectedDescription)
                                    && aCategory.isActive() == expectedIsActive
                                    && aCategory.getId() != null
                                    && aCategory.getCreatedAt() !=null
                                    && aCategory.getUpdatedAt() != null
                                    && aCategory.getDeletedAt() == null;
                        }
                ));

    }
}
