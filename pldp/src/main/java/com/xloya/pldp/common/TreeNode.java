package com.xloya.pldp.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private Integer id;
    private Integer parentId;
    private String title;
    private Boolean spread;


}
