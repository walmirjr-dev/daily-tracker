package service;

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.repository.ChallengeRepository;
import com.walmir.dailytracker.repository.CheckInRepository;
import com.walmir.dailytracker.service.CheckInService;
import com.walmir.dailytracker.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckInServiceTest {

    @Mock
    private CheckInRepository repository;

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private CheckInService checkInService;

    @Test
    void shouldThrowWhenChallengeNotFound() {

        //Arrange
        when(challengeRepository.findById(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> checkInService.undoLastCheckIn(1L));
    }

    @Test
    void shouldThrowWhenCheckInNotFound() {

        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now(), 20, LocalDate.now().plusDays(50));
        when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
        when(repository.findFirstByChallengeOrderByCheckInDateDesc(challenge))
                .thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> checkInService.undoLastCheckIn(1L));
    }

    @Test
    void shouldUndoCheckInSuccessfully() {
        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now().minusDays(10), 20, LocalDate.now().plusDays(50));
        CheckIn checkIn = new CheckIn(1L, LocalDate.now(), challenge);
        when(challengeRepository.findById(challenge.getId())).thenReturn(Optional.of(challenge));
        when(repository.findFirstByChallengeOrderByCheckInDateDesc(challenge))
                .thenReturn(Optional.of(checkIn));
        when(repository.findById(checkIn.getId())).thenReturn(Optional.of(checkIn));

        //Act
        checkInService.undoLastCheckIn(challenge.getId());

        //Assert
        verify(repository, times(1)).deleteById(checkIn.getId());
        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }
}
