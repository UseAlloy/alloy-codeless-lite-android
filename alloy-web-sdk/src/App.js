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

    window.CreateJourneyApplication = async function(settings, journeyData) {
        alloy.init(settings);
        try {
            const result = await alloy.createJourneyApplication(journeyData);
            console.log(result);
            window.CallbackObject.callback(result);
        } catch (e) {
            console.log(JSON.stringify(e));
        }
    }
}

export default App;
