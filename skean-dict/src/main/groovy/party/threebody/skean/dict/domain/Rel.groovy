/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.dict.domain

import party.threebody.skean.data.LastUpdateTime

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import java.time.LocalDateTime

/**
 *
 * @author hzk
 * @since 2017-08-05
 */
class Rel {
    public static int VNO_BATCH = -1
    @Id String key
    @Id String val
    @Id Integer vno // ordinal number to display
    @Column String attr
    @Column String adv

    @Override
    public int hashCode() {
        Objects.hashCode(key) ^ Objects.hashCode(val) ^ Objects.hashCode(attr) ^ Objects.hashCode(vno)
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeRel o = (GeRel) obj;
        return Objects.equals(key, o.key) && Objects.equals(val, o.val) && Objects.equals(vno, o.vno) && Objects.equals(attr, o.attr)
    }

    @Override
    public String toString() {
        "$key 's ${attr} = $val"
    }
}

class GeRel extends Rel {
    @Column String attrx
    @Column String pred
    @Column Integer time1
    @Column Integer time2

    @Override
    public String toString() {
        "$key 's ${attr} $pred $val"
    }
}

/**
 * <h1>Special Relation</h1>
 * attr has only 3 legal values:
 * ALIA - alias
 * SUBS - subset
 * GECH - generic child
 * INST - instance
 */
class SpRel extends Rel {
}

class AliasRel extends SpRel {
    @Column String lang
}

/**
 * <h1>Generic Relation</h1>
 * attr,attrx can be any string
 */
@Table(name = 'dct_rel_ge1')
class Ge1Rel extends GeRel {


}

@Table(name = 'dct_rel_ge2')
class Ge2Rel extends GeRel {
    @Column String valstr       //stringified value
    @Column Integer valnum      //numeric value
    @Column String valmu        //measure unit

    String getVal() {
        valstr ?: valnum
    }
}

@Table(name = 'dct_rel_dual')
class DualRel extends SpRel {
}

