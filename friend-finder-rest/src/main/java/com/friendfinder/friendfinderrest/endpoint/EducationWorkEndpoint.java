package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.workEduDto.EducationCreateRequestDto;
import com.friendfinder.friendfindercommon.dto.workEduDto.EducationCreateResponseDto;
import com.friendfinder.friendfindercommon.dto.workEduDto.WorkExperiencesCreateRequestDto;
import com.friendfinder.friendfindercommon.dto.workEduDto.WorkExperiencesCreateResponseDto;
import com.friendfinder.friendfindercommon.entity.Education;
import com.friendfinder.friendfindercommon.entity.WorkExperiences;
import com.friendfinder.friendfindercommon.mapper.EducationMapper;
import com.friendfinder.friendfindercommon.mapper.WorkExperiencesMapper;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.EducationService;
import com.friendfinder.friendfindercommon.service.WorkExperiencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/work-education")
public class EducationWorkEndpoint {

    private final EducationService educationService;
    private final WorkExperiencesService workExperiencesService;
    private final EducationMapper educationMapper;
    private final WorkExperiencesMapper workExperiencesMapper;

    @PostMapping("/education/add")
    public ResponseEntity<EducationCreateResponseDto> educationAdd(@RequestBody EducationCreateRequestDto education,
                                                                   @AuthenticationPrincipal CurrentUser currentUser) {
        Education map = educationMapper.map(education);
        Education saveEducation = educationService.saveEducation(map, currentUser);
        return ResponseEntity.ok(educationMapper.mapToResponseDto(saveEducation));
    }

    @PostMapping("/work/add")
    public ResponseEntity<WorkExperiencesCreateResponseDto> workAdd(@RequestBody WorkExperiencesCreateRequestDto workExperiences,
                                                                    @AuthenticationPrincipal CurrentUser currentUser) {
        WorkExperiences map = workExperiencesMapper.map(workExperiences);
        WorkExperiences saveWorkExperiences = workExperiencesService.saveWorkExperiences(map, currentUser);
        return ResponseEntity.ok(workExperiencesMapper.mapToResponseDto(saveWorkExperiences));
    }
}
