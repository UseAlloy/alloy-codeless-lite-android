import logo from './logo.svg';
import alloy from '@alloyidentity/web-sdk';
import './App.css';

function App() {
    window.StartAlloy = function(settings) {
        alloy.init(settings);
        let alloyCallback = function(data) {
            let json = JSON.stringify(data);
            console.log(json);
            window.CallbackObject.callback(json);
        }
        alloy.open(alloyCallback, document.getElementById('root'));
    }
}

export default App;
