package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;
  /*
     Getting all comments posted by all users by passing ImageId
     This method will return list of comments
 */
  public List<Comment> getCommentsByImageId(Integer imageId) {
    return commentRepository.getCommentsByImageId(imageId);
  }

  /*
     Posting comment to repository/DB
   */
  public void postComment(Comment newComment) {
    commentRepository.postComment(newComment);
  }


}
