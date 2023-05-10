package com.ll.gramgram.boundedContext.likeablePerson.controller;

import com.ll.gramgram.base.rq.Rq;
import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.service.LikeablePersonService;
import com.ll.gramgram.boundedContext.member.entity.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/usr/likeablePerson")
@RequiredArgsConstructor
public class LikeablePersonController {
    private final Rq rq;
    private final LikeablePersonService likeablePersonService;

    @PreAuthorize("isAuthenticated()") //로그인 여부 확인
    @GetMapping("/like")
    public String showLike() {
        return "usr/likeablePerson/like";
    }

    @AllArgsConstructor
    @Getter
    public static class LikeForm {
        @NotBlank
        @Size(min = 3, max = 30)
        private final String username;

        @NotNull
        @Min(1)
        @Max(3)
        private final int attractiveTypeCode;
    }

    @PreAuthorize("isAuthenticated()") //로그인 여부 확인
    @PostMapping("/like")
    public String like(@Valid LikeForm likeForm) {
        RsData<LikeablePerson> rsData = likeablePersonService.like(rq.getMember(),
                likeForm.getUsername(), likeForm.getAttractiveTypeCode());
        if (rsData.isFail()) {
            return rq.historyBack(rsData);
        }
        return rq.redirectWithMsg("/usr/likeablePerson/list", rsData);
    }

    @PreAuthorize("isAuthenticated()") //로그인 여부 확인
    @GetMapping("/list")
    public String showList(Model model) {
        InstaMember instaMember = rq.getMember().getInstaMember();

        // 인스타인증을 했는지 체크
        if (instaMember != null) {
            List<LikeablePerson> likeablePeople = instaMember.getFromLikeablePeople();
            model.addAttribute("likeablePeople", likeablePeople);
        }

        return "usr/likeablePerson/list";
    }

    @PreAuthorize("isAuthenticated()") //로그인 여부 확인
    @DeleteMapping("/{id}")
    public String cancel(@PathVariable Long id) {

        LikeablePerson likeablePerson = likeablePersonService.findById(id).orElse(null);

        RsData canDeleteRsData = likeablePersonService.canCancel(rq.getMember(), likeablePerson);

        if (canDeleteRsData.isFail()) return rq.historyBack(canDeleteRsData);

        RsData deleteRsData = likeablePersonService.cancel(likeablePerson);

        if (deleteRsData.isFail()) return rq.historyBack(deleteRsData);

        return rq.redirectWithMsg("/usr/likeablePerson/list", deleteRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModify(@PathVariable Long id, Model model) {
        LikeablePerson likeablePerson = likeablePersonService.findById(id).orElseThrow();

        RsData canModifyRsData = likeablePersonService.canModify(rq.getMember(), likeablePerson);

        if (canModifyRsData.isFail()) return rq.historyBack(canModifyRsData);

        model.addAttribute("likeablePerson", likeablePerson);

        return "usr/likeablePerson/modify";
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyForm {
        @NotNull
        @Min(1)
        @Max(3)
        private final int attractiveTypeCode;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, @Valid ModifyForm modifyForm) {
        RsData<LikeablePerson> rsData = likeablePersonService.modifyAttractive(rq.getMember(), id, modifyForm.getAttractiveTypeCode());

        if (rsData.isFail()) {
            return rq.historyBack(rsData);
        }

        return rq.redirectWithMsg("/usr/likeablePerson/list", rsData);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/toList")
    public String showToList(String gender,
                             @RequestParam(value = "attractiveTypeCode", defaultValue = "0") int attractiveTypeCode,
                             @RequestParam(value = "sortCode", defaultValue = "1") int sortCode,
                             Model model) {

        InstaMember instaMember = rq.getMember().getInstaMember();
        Stream<LikeablePerson> filteredPeople = instaMember.getToLikeablePeople().stream();

        // 인스타인증을 했는지 체크
        if (instaMember != null) {
            //당신을 좋아하는 사람들 목록
            if (gender != null) { // 성별로 필터링
                filteredPeople = filteredPeople
                        .filter(person -> person.getFromInstaMember().getGender().equals(gender));
            }
            if (attractiveTypeCode != 0) { // 호감사유로 필터링
                filteredPeople = filteredPeople
                        .filter(person -> person.getAttractiveTypeCode() == attractiveTypeCode);
            }
            if (gender != null && attractiveTypeCode != 0) { // 성별, 호감사유로 필터링 (둘 다 충족)
                filteredPeople = filteredPeople
                        .filter(person -> person.getFromInstaMember().getGender().equals(gender))
                        .filter(person -> person.getAttractiveTypeCode() == attractiveTypeCode);
            }

            Comparator<LikeablePerson> primaryComparator;
            List<LikeablePerson> likeablePeople;
            switch (sortCode) {
                case 2: // 날짜순 (오래전에 받은 호감표시 우선)
                    primaryComparator = Comparator.comparing(LikeablePerson::getCreateDate);
                    break;
                case 3: // 인기 많은 순 member.instaMember.likes
                    primaryComparator = Comparator.comparing(LikeablePerson::getFromInstaMember,
                            Comparator.comparingLong(InstaMember::getLikes).reversed());
                    break;
                case 4: // 인기 적은 순
                    primaryComparator = Comparator.comparing(LikeablePerson::getFromInstaMember,
                            Comparator.comparingLong(InstaMember::getLikes));
                    break;
                case 5: // 성별순 (여성에게 받은 호감표시 먼저)
                    primaryComparator = Comparator.comparing(LikeablePerson::getFromInstaMember,
                            Comparator.comparing(InstaMember::getGender)).reversed();
                    break;
                case 6: // 호감사유순 (외모, 성격, 능력 순)
                    primaryComparator = Comparator.comparing(LikeablePerson::getAttractiveTypeCode);
                    break;
                default: // 기본값인 경우 최신순으로 정렬
                    primaryComparator = Comparator.comparing(LikeablePerson::getCreateDate).reversed();
                    break;
            }
            filteredPeople = filteredPeople.sorted(primaryComparator);
            likeablePeople = filteredPeople.collect(Collectors.toList());
            model.addAttribute("likeablePeople", likeablePeople);
        }
        return "usr/likeablePerson/toList";
    }
}