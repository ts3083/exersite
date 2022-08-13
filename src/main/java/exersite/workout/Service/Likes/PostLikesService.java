package exersite.workout.Service.Likes;

import exersite.workout.Domain.Member.Member;
import exersite.workout.Domain.Post.Post;
import exersite.workout.Domain.likes.PostLikes;
import exersite.workout.Repository.Likes.PostLikesRepository;
import exersite.workout.Service.MemberService;
import exersite.workout.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    private final PostService postService;
    private final MemberService memberService;

    public void clickPostLikes(Long memberId, Long postId) {
        Optional<PostLikes> optionPostLikes = postLikesRepository.findOneByMemberAndPost(memberId, postId);
        if(optionPostLikes.isPresent()) {
            // 이미 좋아요 누른 경우 : 좋아요 삭제
            deletePostLikes(optionPostLikes.get(), postId);
        } else {
            // 좋아요를 누르지 않은 경우
            savePostLikes(memberId, postId);
        }
    }

    private void savePostLikes(Long memberId, Long postId) {
        Member member = memberService.findOne(memberId);
        Post post = postService.findOne(postId);
        PostLikes postLikes = PostLikes.createPostLikes(member, post);
        postLikesRepository.save(postLikes);
    }

    private void deletePostLikes(PostLikes postLikes, Long postId) {
        // 삭제 : 게시글이 가진 Likes Set에서 postLikes 삭제
        Post post = postService.findOne(postId);
        post.getLikes().remove(postLikes);
        postLikesRepository.delete(postLikes);
    }
}
