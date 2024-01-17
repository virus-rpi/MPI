function setAutoReconnect(autoReconnect) {
    fetch('/setAutoReconnect?autoReconnect=' + autoReconnect)
        .then(() => fetch('/getAutoReconnect'))
        .then(response => response.text())
        .then(autoReconnect => {
            document.getElementById('autoReconnectCircle').className = autoReconnect === 'true' ? 'circle green' : 'circle red';
        });
}

function setAutoRespawn(autoRespawn) {
    fetch('/setAutoRespawn?autoRespawn=' + autoRespawn)
        .then(() => fetch('/getAutoRespawn'))
        .then(response => response.text())
        .then(autoRespawn => {
            document.getElementById('autoRespawnCircle').className = autoRespawn === 'true' ? 'circle green' : 'circle red';
        });
}

function setHeadless(headless) {
    fetch('/setHeadless?headless=' + headless)
        .then(() => fetch('/getHeadless'))
        .then(response => response.text())
        .then(headless => {
            document.getElementById('headlessCircle').className = headless === 'true' ? 'circle green' : 'circle red';
        });
}

function connect() {
    let ip = document.getElementById('ip').value;
    let port = document.getElementById('port').value;
    if (port === '') {
        port = '25565';
    }
    if (ip === '') {
        return;
    }
    fetch('/connect?ip=' + ip + '&port=' + port);

    refresh();
}

function disconnect() {
    fetch('/disconnect');
}

function escapeDisconnect() {
    fetch('/escapeDisconnect');
}

function refresh() {
    fetch('/getAutoReconnect')
        .then(response => response.text())
        .then(autoReconnect => {
            document.getElementById('autoReconnectCircle').className = autoReconnect === 'true' ? 'circle green' : 'circle red';
        });

    fetch('/getAutoRespawn')
        .then(response => response.text())
        .then(autoRespawn => {
            document.getElementById('autoRespawnCircle').className = autoRespawn === 'true' ? 'circle green' : 'circle red';
        });

    fetch('/getHeadless')
        .then(response => response.text())
        .then(headless => {
            document.getElementById('headlessCircle').className = headless === 'true' ? 'circle green' : 'circle red';
        });

    fetch('/getScreen')
        .then(response => response.text())
        .then(screen => {
            document.getElementById('screen').innerHTML = screen;
        });

    fetch('/getCurrentAddress')
        .then(response => response.text())
        .then(address => {
            document.getElementById('address').innerHTML = address;
        });

    fetch('/getDisconnectReason')
        .then(response => response.text())
        .then(disconnectReason => {
            document.getElementById('disconnect_reason').innerHTML = disconnectReason;
        });
}

function searchPackets() {
    let searchQuery = document.getElementById('packetSearch').value.toLowerCase();
    let packetListItems = document.querySelectorAll('li');
    for (let i = 0; i < packetListItems.length; i++) {
        if (packetListItems[i].innerText.toLowerCase().includes(searchQuery)) {
            packetListItems[i].style.display = "";
        } else {
            packetListItems[i].style.display = "none";
        }
    }
}

function togglePacketSuppression(packet) { // TODO: if packet is "all", update all buttons
    fetch('/isSuppressed?packet=' + packet)
        .then(response => response.text())
        .then(isSuppressed => {
            fetch('/' + (isSuppressed === 'true' ? 'unsuppressPacket' : 'suppressPacket') + '?packet=' + packet)
                .then(() => {
                    let button = document.getElementById(packet);
                    if (button) { // Check if the button exists
                        if (isSuppressed === 'true') {
                            button.textContent = 'Block';
                            button.style.backgroundColor = 'red';
                        } else {
                            button.textContent = 'Unblock';
                            button.style.backgroundColor = 'green';
                        }
                    }
                });
        });
}

window.onload = function() {
    refresh();
    setInterval(refresh, 1000);
    fetch('/getHTMLForPackets')
        .then(response => response.text())
        .then(html => {
            document.getElementById('packetList').innerHTML = html;
        });
};