package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.ESTITestAnswer;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.ESTITestRepository;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.ESTITestAnswerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ESTITestService {
    private final ESTITestRepository ESTITestRepository;
    private final UserRepository userRepository;

    public void saveAnswer(ESTITestAnswerRequestDto requestDto) {
        ESTITestAnswer ESTITestAnswer = new ESTITestAnswer();
        User respondent = userRepository.find(requestDto.getUserAuth());

        ESTITestAnswer.fillAnswer(
                respondent,
                requestDto.getVersion(),
                requestDto.getAnswer_1(),
                requestDto.getAnswer_2(),
                requestDto.getAnswer_3(),
                requestDto.getAnswer_4(),
                requestDto.getAnswer_5(),
                requestDto.getAnswer_6(),
                requestDto.getAnswer_7(),
                requestDto.getAnswer_8(),
                requestDto.getAnswer_9(),
                requestDto.getAnswer_10(),
                requestDto.getAnswer_11(),
                requestDto.getAnswer_12(),
                requestDto.getAnswer_13()
                );
        ESTITestRepository.save(ESTITestAnswer);
    }
}
