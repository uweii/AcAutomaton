package ACAutomaton;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author wuwei @Date: 2020/9/28
 * @desc: AcTree.
 * usage:
 * 1. create instance
 * 2. call method buildTree {@link AcTree#buildTree(String[])}
 * 3. call method acQuery   {@link AcTree#acQuery(String)}
 */
public class AcTree {
  public AcTreeNode root;

  public AcTree() {
    root = new AcTreeNode();
  }


  /**
   * 构建Trie树， 并构建好fail
   * @param pattern  模式串
   */
  public void buildTree(String[] pattern)  {
    if (pattern == null || pattern.length <= 0){
      throw new IllegalArgumentException("匹配串不允许为空");
    }
    // 将每个字符串都插入到Trie中
    for (int i = 0; i < pattern.length; i++) {
      insertWord(pattern[i]);
    }
    buildFail();
  }

  private void insertWord(String word) {
    char[] chars = word.toCharArray();
    AcTreeNode temp = root;
    for (int i = 0; i < chars.length; i++) {
      if (!temp.subNodeMap.containsKey(chars[i])) {
        temp.subNodeMap.put(chars[i], new AcTreeNode(chars[i]));
      }
      temp = temp.subNodeMap.get(chars[i]);
      if (i == chars.length - 1) {
        temp.exists.add(chars.length);
      }
    }
  }

  // 构建fail指针
  private void buildFail() {
    // root的fail设置为null
    root.fail = null;
    // root的第一层子节点的fail指针都指向root,
    // 并将这两个子节点加入队列中， 进行广度优先搜索
    LinkedList<AcTreeNode> queue = new LinkedList<>();
    root.subNodeMap.forEach(
        (c, node) -> {
          node.fail = root;
          queue.add(node);
        });
    while (!queue.isEmpty()) {
      AcTreeNode cur = queue.poll();
      cur.subNodeMap.forEach(
          (k, v) -> {
            AcTreeNode fafail = cur.fail;
            while (fafail != null && !fafail.subNodeMap.containsKey(k)) {
              fafail = fafail.fail;
            }
            if (fafail == null) { // root节点
              v.fail = root;
            } else {
              v.fail = fafail.subNodeMap.get(k);
            }
            if (v.fail.exists.size() > 0) {
              v.exists.addAll(v.fail.exists);
            }
            queue.push(v);
          });
    }
  }

  /**
   *
   * @param target 需要匹配的母串
   * @return  返回匹配成功的字符串
   */
  public Set<String> acQuery(String target){
    AcTreeNode temp = root;
    char[] chars = target.toCharArray();
    Set<String> res = new HashSet<>();
    for (int i = 0; i < chars.length; i++) {
      while (!temp.subNodeMap.containsKey(chars[i]) && temp.fail != null){
        temp = temp.fail;
      }
      if (temp.subNodeMap.containsKey(chars[i])){
        temp = temp.subNodeMap.get(chars[i]);
      }else {
        continue;
      }
      if (temp.exists.size() > 0 ) {
        int curPos = i;
        temp.exists.forEach(len -> {
          res.add(target.substring(curPos - len + 1, curPos + 1));
        });
      }
    }
    return res;
  }
}
