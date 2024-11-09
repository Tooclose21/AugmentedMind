package com.example.apptest4.ui.userprofile
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apptest4.R
import com.example.apptest4.helpers.ProfileItem
import com.example.apptest4.helpers.createNotification
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.DarkGray
import com.example.apptest4.ui.theme.LightBack
import java.util.Calendar

private val profileItems = listOf(
    ProfileItem(
        navigate = { },
        label = "Help",
        icon = R.drawable.baseline_help_24
    ),
    ProfileItem(
        navigate = { it.startActivity(Intent(it, RemindersActivity::class.java))},
        label = "Manage reminders",
        icon = R.drawable.baseline_notifications_24
    ),
    ProfileItem(
        navigate = {
            it.startActivity(Intent(it, AccountSettings::class.java))
                   },
        label = "Account settings",
        icon = R.drawable.baseline_settings_24
    ),
     ProfileItem(
        navigate = {
            //Session.signOut()
            //it.finish()
        },
        label = "Log out",
        icon = R.drawable.baseline_logout_24
    )
)


@Composable
fun ProfileFragment(activity: ComponentActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 80.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(LightBack)
                .padding(10.dp)
        ) {
            items(profileItems) {
                ProfileListItem(item = it, activity, modifier = Modifier.fillMaxWidth())
            }

        }
    }
}

@Composable
fun ProfileListItem(item: ProfileItem, activity: ComponentActivity, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .clickable { item.navigate(activity) },
            horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .height(50.dp),
            painter = painterResource(item.icon),
            contentDescription = item.label,
            tint = DarkGray
        )
        Text(
            text = item.label,
            style = MaterialTheme.typography.displayMedium,
            color = DarkGreen
        )
    }
}