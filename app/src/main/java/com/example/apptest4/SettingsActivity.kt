package com.example.apptest4
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apptest4.helpers.ProfileItem
import com.example.apptest4.ui.theme.DarkGreen
import com.example.apptest4.ui.theme.DarkGrey
import com.example.apptest4.ui.theme.LightBack

private val profileItems = listOf(
    ProfileItem(
        navigate = { },
        label = "Help",
        icon = R.drawable.baseline_help_24
    ),
    ProfileItem(
        navigate = { },
        label = "Manage reminders",
        icon = R.drawable.baseline_notifications_24
    ),
    ProfileItem(
        navigate = {
            //it.startActivity(Intent(it, StatisticsActivity::class.java))
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
            .padding(30.dp),
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
            .padding(horizontal = 10.dp, vertical = 14.dp)
            .clickable { item.navigate(activity) },
            horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp),
            painter = painterResource(item.icon),
            contentDescription = item.label,
            tint = DarkGrey
        )
        Text(
            text = item.label,
            style = MaterialTheme.typography.displaySmall,
            color = DarkGreen
        )
    }
}