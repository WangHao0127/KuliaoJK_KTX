package com.kuliao.kuliaojk.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.kuliao.kuliaojk.data.User

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:03
 * Description:
 */
@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("DELETE FROM user")
    suspend fun delete()

    @Query("SELECT * FROM user")
    suspend fun getUserInfo():User

}