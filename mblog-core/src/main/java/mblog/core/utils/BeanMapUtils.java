/**
 * 
 */
package mblog.core.utils;

import mblog.core.persist.entity.PostPO;
import mblog.core.persist.entity.UserPO;
import mblog.core.pojos.Attach;
import mblog.core.pojos.Post;
import mblog.core.pojos.User;

import org.springframework.beans.BeanUtils;

/**
 * @author langhsu
 *
 */
public class BeanMapUtils {
	private static String[] USER_IGNORE = new String[]{"password"};
	private static String[] POST_IGNORE = new String[]{"author", "snapshot"};
	private static String[] POST_IGNORE_LIST = new String[]{"author", "snapshot", "markdown", "content"};
	
	public static User copy(UserPO po) {
		if (po == null) {
			return null;
		}
		User ret = new User();
		BeanUtils.copyProperties(po, ret, USER_IGNORE);
		return ret;
	}
	
	public static Post copy(PostPO po, int level) {
		Post d = new Post();
		if (level > 0) {
			BeanUtils.copyProperties(po, d, POST_IGNORE);
		} else {
			BeanUtils.copyProperties(po, d, POST_IGNORE_LIST);
		}
		
		if (po.getAuthor() != null) {
			User u = new User();
			u.setId(po.getAuthor().getId());
			u.setUsername(po.getAuthor().getUsername());
			u.setName(po.getAuthor().getName());
			u.setAvater(po.getAuthor().getAvater());
			d.setAuthor(u);
		}
		if (po.getSnapshot() != null) {
			Attach a = new Attach();
			BeanUtils.copyProperties(po.getSnapshot(), a);
			d.setSnapshot(a);
		}
		return d;
	}
}
