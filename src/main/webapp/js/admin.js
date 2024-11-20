/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


    const ctxCorridas = document.getElementById('corridasChart').getContext('2d');
        const corridasChart = new Chart(ctxCorridas, {
            type: 'line',
            data: {
                labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho'],
                datasets: [{
                    label: 'Corridas Realizadas',
                    data: [50, 70, 90, 65, 85, 100],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: false
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        const ctxFinance = document.getElementById('financeChart').getContext('2d');
        const financeChart = new Chart(ctxFinance, {
            type: 'line',
            data: {
                labels: ['Dia', 'Semana', 'Mês'],
                datasets: [{
                    label: 'Total Corridas',
                    data: [15, 90, 300],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: false
                }, {
                    label: 'Total Lucro (MZN)',
                    data: [3000, 18000, 60000],
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 2,
                    fill: false
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
        
            document.querySelectorAll('.accept-btn').forEach(button => {
            button.addEventListener('click', function() {
                const requestItem = this.closest('.request-item');
                requestItem.querySelector('.request-info').innerHTML += '<p><strong>Status:</strong> Aceito</p>';
            });
        });

        document.querySelectorAll('.reject-btn').forEach(button => {
            button.addEventListener('click', function() {
                const requestItem = this.closest('.request-item');
                requestItem.querySelector('.request-info').innerHTML += '<p><strong>Status:</strong> Rejeitado</p>';
            });
        });
        
 
