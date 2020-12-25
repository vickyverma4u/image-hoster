package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.service.ImageService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

  // Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
  @PersistenceUnit(unitName = "imageHoster")
  private EntityManagerFactory emf;

  @Autowired
  private ImageService imageService;

   /*
     Posting comment to DB if any exception comes then transaction will be rolled back.
   */
  public void postComment(Comment newComment) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();

    try {
      transaction.begin();
      em.persist(newComment);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
    }
  }

  /*
     * This method is fetching all comments by giving imageID
     * SQL 
  */
  public List<Comment> getCommentsByImageId(Integer imageId) {
    Image image = imageService.getImageById(imageId);
    EntityManager em = emf.createEntityManager();
    try {
      TypedQuery<Comment> typedQuery =
          em.createQuery("SELECT c from Comment c where c.image =:image", Comment.class)
              .setParameter("image", image);
      return typedQuery.getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }

}
