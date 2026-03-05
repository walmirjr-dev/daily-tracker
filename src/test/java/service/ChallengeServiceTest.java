package service;

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.dto.ChallengeResponseDTO;
import com.walmir.dailytracker.dto.CheckInResponseDTO;
import com.walmir.dailytracker.repository.ChallengeRepository;
import com.walmir.dailytracker.repository.CheckInRepository;
import com.walmir.dailytracker.service.ChallengeService;
import com.walmir.dailytracker.service.exceptions.BusinessRuleViolationException;
import com.walmir.dailytracker.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    @Mock
    private ChallengeRepository repository;

    @Mock
    private CheckInRepository checkInRepository;

    @InjectMocks
    private ChallengeService challengeService;

    @Test
    void shouldThrowWhenChallengeNotFound() {
        //Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> challengeService.doCheckIn(1L));
    }

    @Test
    void shouldThrowWhenCheckInLimitReached() {

        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now(), 30, LocalDate.now().plusDays(50));
        when(repository.findById(1L)).thenReturn(Optional.of(challenge));
        when(checkInRepository.countByChallengeId(1L)).thenReturn(1L);

        //Act + Assert
        assertThrows(BusinessRuleViolationException.class, () -> challengeService.doCheckIn(1L));
    }

    @Test
    void shouldDoCheckInSuccessfully() {

        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now(), 30, LocalDate.now().plusDays(50));
        when(repository.findById(1L)).thenReturn(Optional.of(challenge));
        when(checkInRepository.countByChallengeId(1L)).thenReturn(0L);

        //Act
        CheckInResponseDTO result = challengeService.doCheckIn(1L);

        //Assert
        assertNotNull(result);
        assertEquals(LocalDate.now(), challenge.getLastCheckInDate());
        verify(repository, times(1)).save(any(Challenge.class));
        verify(checkInRepository, times(1)).save(any(CheckIn.class));
    }

    @Test
    void shouldThrowWhenInitialDateIsAfterEndDate() {
        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now().plusDays(5), 3, LocalDate.now());

        //Act + Assert
        assertThrows(BusinessRuleViolationException.class, () -> challengeService.insert(challenge));
    }

    @Test
    void shouldThrowWhenTargetDaysExceedChallengeDuration() {
        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now(), 30, LocalDate.now().plusDays(5));

        //Act + Assert
        assertThrows(BusinessRuleViolationException.class, () -> challengeService.insert(challenge));
    }

    @Test
    void shouldInsertChallengeSuccessfully() {
        //Arrange
        Challenge challenge = new Challenge(1L, "Test", LocalDate.now(), 30, LocalDate.now().plusDays(50));
        when(repository.save(challenge)).thenReturn(challenge);
        when(checkInRepository.countByChallengeId(1L)).thenReturn(0L);

        //Act
        ChallengeResponseDTO result = challengeService.insert(challenge);

        //Assert
        assertNotNull(result);
        verify(repository, times(1)).save(any(Challenge.class));
    }
}
