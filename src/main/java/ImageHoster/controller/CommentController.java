package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import java.time.LocalDate;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

  @Autowired
  private ImageService imageService;

  @Autowired
  private CommentService commentService;

  /**
   * This Controller method posts a new comment on the image
   * Getting a loggedUser from session
   * creating new object of Comment model class and setting created Date (current date) , User
   * and imageId (getting from imageID) , and text
   * (This Controller was made inorder to pass CommentControllerTest
   * I believe we could implement this logic in ImageController itself )
   *
   * @param imageId this is imageId from path
   * @param imageTitle this is imageTitle from path
   * @param comment this is the comment from textarea
   * @param httpSession to get the loggedin User
   * @return /images/{imageId}
   */
  @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
  public String postComment(
      @PathVariable Integer imageId,
      @PathVariable String imageTitle,
      @RequestParam("comment") String comment,
      HttpSession httpSession) {
    // Getting logged-in user from session
    User user = (User) httpSession.getAttribute("loggeduser");
    Comment newComment = new Comment();
    newComment.setCreatedDate(LocalDate.now());
    newComment.setUser(user);
    newComment.setImage(imageService.getImageById(imageId));
    newComment.setText(comment);
    commentService.postComment(newComment);
    return "redirect:/images/" + imageId;
  }

}
